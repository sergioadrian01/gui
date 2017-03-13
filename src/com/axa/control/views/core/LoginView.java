package com.axa.control.views.core;


import com.axa.control.controllers.core.LoginController;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean(name = "loginView")
@RequestScoped
public class LoginView implements Serializable {

    private static final Logger log = Logger.getLogger(LoginView.class);

    private String username;
    private String password;

    private LoginController loginController;

    @PostConstruct
    public void init() {

        this.loginController = new LoginController();
    }


    public void autenticar() {

        try {
            this.loginController.doLogin(username, password);
        }catch (Exception e){
            log.error(e);
        }

    }

    public void salir(){

        this.loginController.destruirSesion();

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
