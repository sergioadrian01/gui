package com.axa.control.views.core;

import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean (name = "localizacion")
@SessionScoped
public class Localizacion implements Serializable {

    private static final Logger log = Logger.getLogger(Localizacion.class);

    private Locale locale;

    public Localizacion() {
        log.trace("Constructor Localizacion()");
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        log.debug("locale: " + locale);
    }

    public String getLanguage(){
        return locale.getLanguage();
    }

    public void setLanguage(final String language){
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public Locale getLocale() {
        return locale;
    }
}
