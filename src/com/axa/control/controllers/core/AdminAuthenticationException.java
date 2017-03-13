package com.axa.control.controllers.core;

public class AdminAuthenticationException extends Exception {

    public AdminAuthenticationException(String message){

        super(message);

    }

    public AdminAuthenticationException(String message, Throwable cause){

        super(message, cause);

    }

}
