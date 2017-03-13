package com.axa.control.enums;

/**
 * Created by Erick Libreros on 04/11/2015.
 */
public enum LdapErrors {

    USER_NOT_FOUND_WIN("AcceptSecurityContext error, data 525"),
    USER_NOT_FOUND_ZEN("NT_STATUS_NO_SUCH_USER"),

    INVALID_CREDENTIALS_WIN("AcceptSecurityContext error, data 52e"),
    INVALID_CREDENTIALS_ZEN("NT_STATUS_LOGON_FAILURE"),

    PASSWORD_EXPIRED_WIN("AcceptSecurityContext error, data 532"),
    PASSWORD_EXPIRED_ZEN("NT_STATUS_PASSWORD_EXPIRED"),

    PASSWORD_MUST_CHANGE_WIN("AcceptSecurityContext error, data 773"),
    PASSWORD_MUST_CHANGE_ZEN("NT_STATUS_PASSWORD_MUST_CHANGE"),

    PASSWORD_RESTRICTIONS_WIN("(CONSTRAINT_ATT_TYPE), data 0, Att 9005a"),
    PASSWORD_RESTRICTIONS_ZEN("check_password_restrictions"),

    ACCOUNT_DISABLED_WIN("AcceptSecurityContext error, data 533"),
    ACCOUNT_DISABLED_ZEN("NT_STATUS_ACCOUNT_DISABLED"),

    ACCOUNT_LOCKED_WIN("AcceptSecurityContext error, data 775"),
    ACCOUNT_LOCKED_ZEN("NT_STATUS_ACCOUNT_LOCKED_OUT"),

    INSUFFICIENT_ACCESS_RIGHTS_WIN("problem 4003 (INSUFF_ACCESS_RIGHTS)"),
    INSUFFICIENT_ACCESS_RIGHTS_ZEN("error code 50");

    private String error;

    LdapErrors(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}