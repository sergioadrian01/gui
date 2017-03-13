package com.axa.control.controllers.core;

import com.axa.control.controllers.commons.SessionManager;
import com.axa.control.enums.SessionObjects;
import com.axa.control.models.Ldap;
import com.axa.control.models.Roles;
import org.apache.log4j.Logger;

import java.util.List;


public class ProfileController {

    private final static Logger log = Logger.getLogger(LdapOperations.class);


    LdapOperations operations;

    public ProfileController() throws AdminAuthenticationException{

        SessionManager sessionManager = new SessionManager();
        Ldap ldapobject = (Ldap) sessionManager.getObject(SessionObjects.LDAP);
        this.operations = new LdapOperations(ldapobject);

    }


    public String fetchName(String account) {

        String name = this.operations.getAttributeOfUser(account, "cn");

        return name;
    }


    public Roles fechRoles(String account) {

        Roles roles = new Roles();
        List<String> genlist = this.operations.getUserRoles(account);


        log.debug("Account:" + account);

        log.debug("Obteniendo roles:" + genlist.size());
        for (String rol : genlist) {

            if (rol.contentEquals("scan")) {
                roles.setScan(true);
            }

            if (rol.contentEquals("spark")) {
                roles.setSpark(true);
            }

            if(rol.contentEquals("punch")){
                roles.setPunch(true);
            }

            if(rol.contentEquals("admin")){
                roles.setAdmin(true);
            }

        }

        return roles;
    }

}