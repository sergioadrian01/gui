package com.axa.control.controllers.core;

import com.axa.control.enums.LdapErrors;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.*;
import org.springframework.ldap.InvalidAttributeValueException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import com.axa.control.models.Ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class LdapOperations {

    private final static Logger log = Logger.getLogger(LdapOperations.class);

    private LdapTemplate ldapTemplateContext;
    private Ldap ldap;


    public LdapOperations(Ldap ldap) throws AdminAuthenticationException {

        this.createLdapContext(ldap);
        this.ldap = ldap;

    }


    private void createLdapContext(Ldap ldap) throws AdminAuthenticationException {

        LdapContextSource contextSource = new LdapContextSource();

        HashMap<String, Object> env = new HashMap<>();

        if (ldap.getSsl_connection() != null && ldap.getSsl_connection()) {
            contextSource.setUrl("ldaps://" + ldap.getHost() + ":" + ldap.getPort());

            env.put(Context.SECURITY_PROTOCOL, "ssl");

            char sep = File.separatorChar;

            String javaHome = System.getProperty("java.home");
            String keystore = javaHome + sep + "lib" + sep + "security" + sep + "cacerts";

            log.debug("Path to Certificates: " +  keystore);

            System.setProperty("javax.net.ssl.trustStore", keystore);

        } else
            contextSource.setUrl("ldap://" + ldap.getHost() + ":" + ldap.getPort());

        env.put("com.sun.jndi.ldap.connect.pool", "true");
        env.put("com.sun.jndi.ldap.connect.pool.timeout", "10000");

        contextSource.setBaseEnvironmentProperties(env);
        contextSource.setPooled(true);

        contextSource.setUserDn(ldap.getUser());
        contextSource.setPassword(ldap.getPasswd());
        contextSource.setBase(ldap.getDc());



        try {

            contextSource.afterPropertiesSet();

            if (ldap.getSsl_connection() != null && ldap.getSsl_connection()){
                log.info("Estableciendo certificado para comunicaciòn ssl....");

                contextSource.getReadWriteContext();

            }

            contextSource.getContext(ldap.getUser(), ldap.getPasswd());

        } catch (Exception e) {
            log.error("Exception while creating ldap context: " + e);


            for (LdapErrors le : LdapErrors.values())

                if (e.getMessage().contains(le.getError()))
                    throw new AdminAuthenticationException(le.toString());

        }
        LdapTemplate ldapTemplate = new LdapTemplate();
        ldapTemplate.setContextSource(contextSource);
        this.ldapTemplateContext = ldapTemplate;
        this.ldapTemplateContext.setIgnorePartialResultException(true);

        log.info("Contexto administrativo de ldap creado exitosamente....");

    }


    public String getRestriction(String usr, String password) {

        try {

            this.ldapTemplateContext.authenticate(query().where("saMAccountName").is(usr), password);
            log.info("Credentials match....");

        } catch (AuthenticationException e) {

            for (LdapErrors le : LdapErrors.values())

                if (e.getMessage().contains(le.getError()))
                    log.warn("User exception type: " + le);

            log.warn("AuthenticationException: " + e.getMessage());
            return e.getMessage();

        } catch (EmptyResultDataAccessException e) {

            log.warn("Invalid user '" + usr + "': " + e);
            return "INVALID";

        } catch (CommunicationException ce) {

            log.warn("Exception in comunication: " + ce);

            return "ERROR";

        } catch (UncategorizedLdapException ue){

            if (ue.toString().contains("AuthenticationException")){

                for (LdapErrors le : LdapErrors.values())

                    if (ue.getMessage().contains(le.getError()))
                        log.warn("User exception type: " + le);

                log.warn("AuthenticationException: " + ue.getMessage());
                return ue.getMessage();


            } else if (ue.toString().contains("CommunicationException")){

                log.warn("Exception in comunication: " + ue);

                return "ERROR";

            } else {

                log.error("Server catch exception: " + ue);
                return "ERROR";

            }

        }

        return "CORRECT";

    }


    public String getAttributeOfUser(String user, String attribute) {

        String value = "None";

        try {

            DirContextOperations context = this.ldapTemplateContext.searchForContext(query().where("saMAccountName").is(user));
            value = (String) context.getObjectAttribute(attribute);

            log.info("Closing ldap context....");
            context.close();

        } catch (org.springframework.ldap.NameNotFoundException | EmptyResultDataAccessException e) {

            log.error("The user was not found, couldn't get the business unit\n");
            e.printStackTrace();

        } catch (org.springframework.ldap.CommunicationException c) {

            log.error("Error de comunicación");


        } catch (AuthenticationException e) {

            log.error("Exception while creating ldap context: " + e);


            for (LdapErrors le : LdapErrors.values())

                if (e.getMessage().contains(le.getError()))
                    log.error("Admin ldap context: " + le);

        } catch (Exception e) {

            log.error("Exception: " + e);


        }

        return value;

    }


    public Boolean changePasswordAdmin(String user, String oldPassword, String newPassword) {

        Boolean success = true;
        String admPassword = "adminPassword123";
        String pwdLastSet = this.getAttributeOfUser(user, "pwdLastSet");
        String minPwdAge = this.getAttributeOfDomain("minPwdAge");
        String simpleDN = getUserDN(user);
        String domain = ldap.getDc();

        Attribute newattr = this.getUnicodeAttribute("\"" + newPassword + "\"");
        Attribute admattr = this.getUnicodeAttribute("\"" + admPassword + "\"");
        Attribute oldattr = this.getUnicodeAttribute("\"" + oldPassword + "\"");
        Attribute restoreLastSetAttr = new BasicAttribute("pwdLastSet", pwdLastSet);
        Attribute restorePwdAgeAttr = new BasicAttribute("minPwdAge", minPwdAge);

        ModificationItem[] modifyItemsUser = this.getModification(false, admattr, newattr);
        ModificationItem[] modifyItemAdmin = this.getModification(true, admattr);
        ModificationItem[] restorePwdAge = this.getModification(true, restorePwdAgeAttr);
        ModificationItem[] restoreLastSet = this.getModification(true, restoreLastSetAttr);
        ModificationItem[] restoreOldPwd = this.getModification(true, oldattr);

        try {

            log.info("Checking if user has your old credentials correct....");
            String credentials = this.getRestriction(user, oldPassword);

            if (!credentials.equals("INVALID") && !credentials.equals("ERROR") &&
                    !credentials.contains(LdapErrors.INVALID_CREDENTIALS_WIN.getError()) &&
                    !credentials.contains(LdapErrors.INVALID_CREDENTIALS_ZEN.getError())) {

                if (minPwdAge.equals("0"))
                    this.changePwdNoMinAge(simpleDN, domain, admPassword, modifyItemAdmin, modifyItemsUser);

                else {

                    if (credentials.contains(LdapErrors.PASSWORD_EXPIRED_WIN.getError()) ||
                            credentials.contains(LdapErrors.PASSWORD_EXPIRED_ZEN.getError()))

                        success = this.changePwdMinAge(pwdLastSet, minPwdAge, simpleDN, domain, admPassword, true,
                                modifyItemAdmin, modifyItemsUser, restorePwdAge);
                    else

                        success = this.changePwdMinAge(pwdLastSet, minPwdAge, simpleDN, domain, admPassword, false,
                                modifyItemAdmin, modifyItemsUser, restorePwdAge);

                }


            } else if (!credentials.equals("ERROR")) {


                success = false;

            }

        } catch (AuthenticationException e) {
            log.warn("AuthenticationException: " + e);

            success = false;

        } catch (NamingException | InvalidAttributeValueException | NoPermissionException e) {
            log.warn("NamingException: " + e);


            String pwdLastSetAfter = this.getAttributeOfUser(user, "pwdLastSet");
            String minPwdAgeAfter = this.getAttributeOfDomain("minPwdAge");

            if (!pwdLastSet.equals(pwdLastSetAfter)){

                log.info("Restoring last password set....");
                ldapTemplateContext.modifyAttributes(simpleDN, restoreLastSet);

            }

            if (!minPwdAge.equals(minPwdAgeAfter)){

                log.info("Restoring min password age....");
                ldapTemplateContext.modifyAttributes("", restorePwdAge);

            }

            log.info("Restoring old password....");
            ldapTemplateContext.modifyAttributes(simpleDN, restoreOldPwd);


            passwordComplexMessage(e.getMessage());
            success = false;

        } catch (OperationNotSupportedException e) {
            log.error("Needed a certificate to perform this operation....\n" + e);


            success = false;
        }

        return success;

    }


    private void changePwdNoMinAge(String simpleDN, String domain, String password, ModificationItem[]... items)
            throws AuthenticationException, NamingException, InvalidAttributeValueException, NoPermissionException,
            OperationNotSupportedException {
        log.info("Password change without minimum password age....");

        ldapTemplateContext.modifyAttributes(simpleDN, items[0]);
        log.info("Password modified as Administrator....");

        DirContext userContext = ldapTemplateContext.getContextSource().getContext(simpleDN + "," + domain, password);
        userContext.modifyAttributes(simpleDN, items[1]);
        log.info("Password modification from user context....");

        log.debug("Password changed");

        log.info("Closing ldap context....");
        userContext.close();

    }


    private boolean changePwdMinAge(String pwdLastSet, String minPwdAge, String simpleDN, String domain, String password,
                                    boolean expired, ModificationItem[]... items) throws AuthenticationException, NamingException,
            InvalidAttributeValueException, NoPermissionException, OperationNotSupportedException {
        log.info("Password change with minimum password age....");

        int diffInDays = this.getDiffInDays(pwdLastSet);
        int minPwdAgeDays = this.nanoToDays(minPwdAge);
        if (pwdLastSet.equals("0") || expired) {
            Attribute pwdAgeAttr = new BasicAttribute("minPwdAge", "0");
            ModificationItem[] pwdAge = this.getModification(true, pwdAgeAttr);
            ldapTemplateContext.modifyAttributes("", pwdAge);

            changePwdNoMinAge(simpleDN, domain, password, items[0], items[1]);

            ldapTemplateContext.modifyAttributes("", items[2]);
        } else if (minPwdAgeDays <= diffInDays)
            changePwdNoMinAge(simpleDN, domain, password, items[0], items[1]);

        else {
            log.warn("Password is too young for change....");


            return false;
        }

        return true;

    }


    private ModificationItem[] getModification(boolean replace, Attribute... params) {

        ModificationItem[] modificationItems = new ModificationItem[params.length];

        if (replace)
            modificationItems[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, params[0]);

        else {

            modificationItems[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, params[0]);
            modificationItems[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, params[1]);

        }

        return modificationItems;

    }


    private void passwordComplexMessage(String error) {

        if (error.contains(LdapErrors.PASSWORD_RESTRICTIONS_WIN.getError()) ||
                error.contains(LdapErrors.PASSWORD_RESTRICTIONS_ZEN.getError())) {

            String minPwdLength = this.getAttributeOfDomain("minPwdLength");
            String pwdProperties = this.getAttributeOfDomain("pwdProperties");
            String pwdHistoryLength = this.getAttributeOfDomain("pwdHistoryLength");

            log.debug("Min password length: " + minPwdLength);
            log.debug("Password properties: " + pwdProperties);
            log.debug("Password history length: " + pwdHistoryLength);


            log.warn("PASSWORD_RESTRICTIONS");

        } else if (error.contains(LdapErrors.INSUFFICIENT_ACCESS_RIGHTS_WIN.getError()) ||
                error.contains(LdapErrors.INSUFFICIENT_ACCESS_RIGHTS_ZEN.getError())) {


            log.error("Admin ldap Exception: INSUFFICIENT_ACCESS_RIGHTS");

        } else{

        }


    }


    public String getAttributeOfDomain(String attribute) {

        String atributo;

        String domain = ldap.getDc();

        try {

            DirContextOperations context = this.ldapTemplateContext.searchForContext(query().where("distinguishedName").is(domain));

            atributo = (String) context.getObjectAttribute(attribute);

            log.info("Closing ldap context....");
            context.close();

        } catch (org.springframework.ldap.NameNotFoundException e) {

            log.error("The user was not found, couldn't get the business unit");
            atributo = "None";

        } catch (org.springframework.ldap.CommunicationException c) {

            log.error("Error de comunicación");
            atributo = "None";

        } catch (Exception e) {

            log.error("Exception: " + e);
            e.printStackTrace();
            atributo = "None";

        }

        return atributo;

    }


    public List<String> getUserRoles(String user) {

        List<String> roles;
        List<String> cleaned = new ArrayList<>();

        try {
            DirContextOperations context = this.ldapTemplateContext.searchForContext(query().where("saMAccountName").is(user));
            DirContextAdapter adapter = (DirContextAdapter) context;

            log.debug("Context:" + context.getDn());

            cleaned = new ArrayList<>();

            roles = Arrays.asList(adapter.getStringAttributes("memberOf"));

            log.debug("Number of roles found: " + roles.size());

            for (String var : roles) {

                log.debug(var);
            }

            log.debug("Parsing & cleaning roles list");
            for (String name : roles) {

                String cleanedName = name.substring(name.indexOf("CN=") + 3, name.indexOf(",")).toLowerCase();

                cleaned.add(cleanedName);
            }

            log.info("Closing ldap context....");
            context.close();

        } catch (org.springframework.ldap.NameNotFoundException e) {
            log.error("Error al obtener los grupos a los que pertenece el usuario: " + e);

        } catch (NamingException e) {
            log.error("Error al obtener los grupos a los que pertenece el usuario: " + e);
        }catch (UnsupportedOperationException e){
            log.error(e);
        }

        return cleaned;
    }


    public void addAttribute(String user, String attribute, String value) {

        try {

            log.debug("Adding atribute:" + attribute + "=" + value + "to user: " + user);
            DirContextOperations context = this.ldapTemplateContext.searchForContext(query().where("saMAccountName").is(user));
            context.setAttributeValue(attribute, value);
            ldapTemplateContext.modifyAttributes(context);

            log.info("Closing ldap context....");
            context.close();

        } catch (NamingException e){
            log.error("Exception: " + e);
        }

    }


    public String getUserDN(String user) {

        DirContextOperations context = this.ldapTemplateContext.searchForContext(query().where("saMAccountName").is(user));
        String dn = context.getNameInNamespace();

        String[] values = dn.split(",");
        String simpleDN = "";

        for (int i = 0; i <= values.length - 3; i++) {

            simpleDN += values[i];
            simpleDN += i == values.length - 3 ? "" : ",";

        }
        try {
            log.info("Closing ldap context....");
            context.close();
        }  catch (NamingException e){
            log.error("Exception: " + e);
        }

        return simpleDN;

    }


    private Attribute getUnicodeAttribute(String quoted) {

        try {

            return new BasicAttribute("unicodePwd", quoted.getBytes("UTF-16LE"));

        } catch (UnsupportedEncodingException e) {

            log.warn("Unable to get bytes from string: " + e);

        }

        return null;

    }


    private int getDiffInDays(String value) {
        long adDateTime = Long.parseLong(value);
        long milliseconds = (adDateTime / 10000) - 11644473600000L;
        Date pwdLastSetDate = new Date(milliseconds);
        return (int) ((new Date().getTime() - pwdLastSetDate.getTime()) / (1000 * 60 * 60 * 24));
    }


    private int nanoToDays(String value) {
        long minPwdAgeLong = Math.abs(Long.parseLong(value));
        long minPwdAgeSecs = minPwdAgeLong / 10000000;
        return (int) (minPwdAgeSecs / 86400);
    }

}