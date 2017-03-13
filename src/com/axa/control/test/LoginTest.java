package com.axa.control.test;

import com.axa.control.controllers.core.LoginController;
import com.axa.control.models.Ldap;
import org.apache.log4j.Logger;

/**
 * Created by seaxom on 27/12/16.
 */
public class LoginTest {

    private final static Logger log = Logger.getLogger(LoginTest.class);

    public static void main(String args[]){

        log.debug("Probando");
        Ldap ldap = new Ldap();

        ldap.setBase_group("Users");
        ldap.setHost("10.133.9.92");
        ldap.setName("Zentyal-domain.lan");
        ldap.setPort("389");
        ldap.setDc("dc=zentyal-domain, dc=lan");
        ldap.setUser("controller@zentyal-domain.lan");
        ldap.setPasswd("H567kil18A");
        ldap.setSsl_connection(false);
        ldap.setType("Apache");


        try {
            LoginController loginController = new LoginController();
            loginController.doLogin("sergio", "H567kil18A");
        }catch (Exception e){
            log.error(e);
        }

/*
        try {
            LdapOperations operations = new LdapOperations(ldap);
            List<String> roles = operations.getUserRoles("sergio");

            String name = operations.getAttributeOfUser("sergio", "cn");
            log.debug("Nombre:" + name);

            log.debug("Obteniendo roles:" + roles.size());
            for(String rol : roles){
                log.debug(rol);
            }




        }catch (AdminAuthenticationException e){
            log.debug(e);
        }

*/



    }
}
