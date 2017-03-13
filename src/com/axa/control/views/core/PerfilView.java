package com.axa.control.views.core;

import com.axa.control.controllers.commons.SessionManager;

import com.axa.control.controllers.core.AdminAuthenticationException;
import com.axa.control.controllers.core.ProfileController;
import com.axa.control.enums.SessionObjects;
import com.axa.control.models.Roles;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "perfilView")
@ViewScoped
public class PerfilView {
    private static final Logger log = Logger.getLogger(PerfilView.class);


    private Boolean spark;
    private Boolean scan;
    private Boolean punch;
    private Boolean admin;


    private String name;
    private String acct;
    private String pass;

    private SessionManager sessionManager;


    public PerfilView() throws AdminAuthenticationException {

        this.sessionManager = new SessionManager();
        ProfileController profileController = new ProfileController();

        String username = (String) sessionManager.getObject(SessionObjects.USER);
        String passwd = (String) sessionManager.getObject(SessionObjects.PASSWORD);

        this.acct = username;
        this.pass = passwd;

        this.name = profileController.fetchName(username);
        this.sessionManager.setObject(SessionObjects.FULLNAME, name);

        Roles roles = profileController.fechRoles(username);

        this.spark = roles.getSpark();
        this.scan = roles.getScan();
        this.punch = roles.getPunch();
        this.admin = roles.getAdmin();

    }

    @PostConstruct
    public void init() {


    }


    public Boolean getSpark() {
        return spark;
    }

    public void setSpark(Boolean spark) {
        this.spark = spark;
    }

    public Boolean getScan() {
        return scan;
    }

    public void setScan(Boolean scan) {
        this.scan = scan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPunch() {
        return punch;
    }

    public void setPunch(Boolean punch) {
        this.punch = punch;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }
}
