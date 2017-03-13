package com.axa.control.controllers.commons;


import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;


public class SessionManager implements Serializable {


    public Object getObject(String name) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);

        return httpSession.getAttribute(name);
    }

    public void setObject(String name, Object objeto) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);

        httpSession.setAttribute(name, objeto);


    }

    public void deleteObject(String name) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);

        httpSession.removeAttribute(name);

    }

    public List<String> getSessionObjectList() {

        List<String> listaObjetos = null;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        Enumeration e = httpSession.getAttributeNames();
        if (e != null) {
            listaObjetos = new LinkedList<>();
            while (e.hasMoreElements()) {
                listaObjetos.add(e.nextElement().toString());
            }
        }
        return listaObjetos;
    }

}
