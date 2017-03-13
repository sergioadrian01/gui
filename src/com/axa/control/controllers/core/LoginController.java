package com.axa.control.controllers.core;


import com.axa.control.controllers.commons.SessionManager;
import com.axa.control.enums.SessionObjects;
import com.axa.control.models.Ldap;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;


public class LoginController {

    private final static Logger log = Logger.getLogger(LoginController.class);


    private Ldap ldapObject;

    public LoginController() {

        Ldap ldap = new Ldap();

        ldap.setBase_group("Users");
        ldap.setHost("192.168.56.101");
        ldap.setName("Zentyal-domain.lan");
        ldap.setPort("389");
        ldap.setDc("dc=zentyal-domain, dc=lan");
        ldap.setUser("controller@zentyal-domain.lan");
        ldap.setPasswd("H567kil18A");
        ldap.setSsl_connection(false);
        ldap.setType("Apache");

        ldapObject = ldap;


    }

    public void doLogin(String username, String passwd) throws Exception {


        Subject currentUser = this.authenticate(username, passwd, ldapObject);
        Boolean respuesta = currentUser.isAuthenticated();

        if (currentUser.isAuthenticated()) {
            log.info("User authenticated....");

            SessionManager sessionManager = new SessionManager();
            sessionManager.setObject(SessionObjects.USER, username);
            sessionManager.setObject(SessionObjects.PASSWORD, passwd);
            sessionManager.setObject(SessionObjects.LDAP, ldapObject);

            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/modulo/layout/index.xhtml");


        } else {

            log.info("bad password");
            throw new AuthenticationException();
        }


    }


    private Subject authenticate(String usr, String password, Ldap ldap) {

        String domain = this.generateDomain(ldap.getDc());
        String username = usr + "@" + domain;


        IniSecurityManagerFactory factory = new IniSecurityManagerFactory(this.getShiroProperties(ldap));
        org.apache.shiro.mgt.SecurityManager securityManager = factory.createInstance();

        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        Subject currentUser = new Subject.Builder().buildSubject();



        try {



            log.debug("Checking if user : " + username + ", is a valid user.");

            currentUser.login(token);
            log.debug("Access allowed");


        } catch (AuthenticationException e) {

            log.debug("Access denied, notifying user: " + e.getLocalizedMessage());


        }


        return currentUser;

    }


    private Ini getShiroProperties(Ldap ldap) {

        Ini ini = new Ini();
        ini.setSectionProperty("main", "contextFactory", "org.apache.shiro.realm.ldap.JndiLdapContextFactory");

        if (ldap.getSsl_connection() != null && ldap.getSsl_connection()) {

            ini.setSectionProperty("main", "contextFactory.url", "ldaps://" + ldap.getHost() + ":" + ldap.getPort());
            ini.setSectionProperty("main", "contextFactory.environment[java.naming.security.protocol]", "ssl");

        } else
            ini.setSectionProperty("main", "contextFactory.url", "ldap://" + ldap.getHost() + ":" + ldap.getPort());

        ini.setSectionProperty("main", "contextFactory.systemUsername", ldap.getUser());
        ini.setSectionProperty("main", "contextFactory.systemPassword", ldap.getPasswd());
        ini.setSectionProperty("main", "realm", "org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm");
        ini.setSectionProperty("main", "realm.ldapContextFactory", "$contextFactory");
        ini.setSectionProperty("main", "realm.authorizationCachingEnabled", "false");

        return ini;

    }


    public void destruirSesion() {

        log.debug("Cerrando sesión");
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/index.xhtml");

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

    }

    private String generateDomain(String dcsyntax) {

        String line = dcsyntax;
        String[] values = line.split(",");

        Map<Integer, String> dcList = new HashMap<>(1000);

        Integer index = new Integer(0);
        for (String value : values) {
            dcList.put(index, (value.replace("dc=", "").trim()));
            index++;
        }

        //Concat domain
        String domain = "";

        log.debug("Size:" + dcList.size());
        Integer lastItem = dcList.size() - 1;

        for (Map.Entry entry : dcList.entrySet()) {
            log.debug(entry.getKey() + ", " + entry.getValue());


            if (entry.getKey() != lastItem) {

                domain = domain.concat(entry.getValue() + ".");

            } else {

                domain = domain.concat((String) entry.getValue());
            }

        }


        log.debug(domain);
        return domain;
    }


}