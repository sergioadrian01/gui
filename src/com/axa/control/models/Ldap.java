package com.axa.control.models;


import java.math.BigInteger;

public class Ldap implements java.io.Serializable {
    private BigInteger id;
    private String name;
    private String host;
    private String port;
    private String user;
    private String passwd;
    private String dc;
    private String base_group;
    private String type;
    private Boolean ssl_connection;

    public Ldap() {

    }

    public Ldap(String name, String host, String port, String user, String passwd, String dc, String base_group, String type, Boolean ssl_connection) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.user = user;
        this.passwd = passwd;
        this.dc = dc;
        this.base_group = base_group;
        this.type = type;
        this.ssl_connection = ssl_connection;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getBase_group() {
        return base_group;
    }

    public void setBase_group(String base_group) {
        this.base_group = base_group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSsl_connection() {
        return ssl_connection;
    }

    public void setSsl_connection(Boolean ssl_connection) {
        this.ssl_connection = ssl_connection;
    }
}
