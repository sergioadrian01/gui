package com.axa.control.views.core;

import com.axa.control.controllers.commons.SessionManager;
import com.axa.control.daos.PunchRecordDAO;
import com.axa.control.enums.SessionObjects;
import com.axa.control.models.PunchRecord;
import com.mongodb.MongoClient;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "coordenadas")
@ViewScoped
public class Coordenadas {

    private static final Logger log = Logger.getLogger(Coordenadas.class);

    private String longitud;
    private String latitud;

    private String selecLongitud;
    private String selecLatitud;

    private Boolean entrada;
    private Boolean comida;
    private Boolean regreso;
    private Boolean salida;

    private List<PunchRecord> punchs;

    public static final String DB_NAME = "axa";

    public Coordenadas() {
        this.longitud = "37.7";
        this.latitud = "22.43";

        this.entrada = false;
        this.comida = false;
        this.regreso = false;
        this.salida = false;
    }

    public void registroEntrada() {

        log.debug("Registrando entrada");


        this.latitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
        this.longitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

        log.debug("x: " + longitud);
        log.debug("y: " + latitud);

        persistir("Entrada", latitud, longitud);

        log.debug("Deshabilitando boton de entrada");
        this.entrada = true;

    }

    public void registroComida() {

        log.debug("Registrando comida");


        this.latitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
        this.longitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

        log.debug("x: " + longitud);
        log.debug("y: " + latitud);

        persistir("Comida", latitud, longitud);

        log.debug("Deshabilitando boton de comida");
        this.comida = true;

    }

    public void registroRegreso() {

        log.debug("Registrando regreso");


        this.latitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
        this.longitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

        log.debug("x: " + longitud);
        log.debug("y: " + latitud);

        persistir("Regreso", latitud, longitud);

        log.debug("Deshabilitando boton de regreso");
        this.regreso = true;

    }

    public void registroSalida() {

        log.debug("Registrando salida");


        this.latitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
        this.longitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

        log.debug("x: " + longitud);
        log.debug("y: " + latitud);

        persistir("Salida", latitud, longitud);
        log.debug("Deshabilitando boton de salida");
        this.salida = true;

    }


    private void persistir(String evento, String latitud, String longitud) {

        try {

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);

            SessionManager sessionManager = new SessionManager();
            String username = (String) sessionManager.getObject(SessionObjects.USER);
            String fullname = (String) sessionManager.getObject(SessionObjects.FULLNAME);

            PunchRecord punch = new PunchRecord();
            punch.setFullname(fullname);
            punch.setAccount(username);
            punch.setTimeStamp(strDate);
            punch.setEvent(evento);
            punch.setLatitud(latitud);
            punch.setLongitud(longitud);


            MongoClient mongo = new MongoClient("127.0.0.1", 27017);
            Datastore datastore = new Morphia().mapPackage("com.axa.control.models").createDatastore(mongo, DB_NAME);

            PunchRecordDAO dao = new PunchRecordDAO(PunchRecord.class, datastore);
            dao.save(punch);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void obtenerRegistros() {

        try {

            MongoClient mongo = new MongoClient("127.0.0.1", 27017);
            Datastore datastore = new Morphia().mapPackage("com.axa.control.models").createDatastore(mongo, DB_NAME);

            PunchRecordDAO dao = new PunchRecordDAO(PunchRecord.class, datastore);
            this.punchs = dao.findAll();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String actualizarMapa() {
        log.debug("Entrando al metodo");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String lat = params.get("latitud");
        String longi = params.get("longitud");

        log.debug(lat);
        log.debug(longi);

        return "";
    }

    public String getLatitud() {
        log.debug("Valor de latitud:" + latitud);
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        log.debug("Valor de longitud:" + longitud);
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public Boolean getComida() {
        return comida;
    }

    public void setComida(Boolean comida) {
        this.comida = comida;
    }

    public Boolean getRegreso() {
        return regreso;
    }

    public void setRegreso(Boolean regreso) {
        this.regreso = regreso;
    }

    public Boolean getSalida() {
        return salida;
    }

    public void setSalida(Boolean salida) {
        this.salida = salida;
    }

    public List<PunchRecord> getPunchs() {
        this.obtenerRegistros();
        return punchs;
    }

    public void setPunchs(List<PunchRecord> punchs) {
        this.punchs = punchs;
    }

    public String getSelecLongitud() {
        return selecLongitud;
    }

    public void setSelecLongitud(String selecLongitud) {
        this.selecLongitud = selecLongitud;
    }

    public String getSelecLatitud() {
        return selecLatitud;
    }

    public void setSelecLatitud(String selecLatitud) {
        this.selecLatitud = selecLatitud;
    }
}
