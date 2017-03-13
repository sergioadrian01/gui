package com.axa.control.controllers.commons;

import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;


public class FacesMessages {

    private static final Logger log = Logger.getLogger(FacesMessages.class);

    public static void addedRecord() {
        try {
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.registro.exitoso", "srvdsk"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        catch (Exception e){}

    }

    public static void deletedRecord() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.eliminacion.exitoso", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void updatedRecord() {
        try {
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.actualizacion.exitoso", "srvdsk"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        catch (Exception e){}

    }

    public static void archivoCargado() {
        try {
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.carga", "srvdsk"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        catch (Exception e){}

    }


    public static void configuracionIncorrecta() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("mensaje.configuracion.incorrecta", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void accesoDenegado() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("mensaje.autenticacion.error", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void cuentaBloqueada() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("mensaje.autenticacion.bloqueo", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void errorConexion() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("mensaje.autenticacion.conexion", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }



    public static void errorMessage(String message) {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), message);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void infoMessage(String message) {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), message);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void databaseErrorConnection(String details) {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("db.connection", "connection") + " " + details);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void migrationSummary(int existing, int created) {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("db.created", "connection") + " " + created + ", " + obtenerTag("db.existing", "connection") + " " + existing);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void informacionNoValida() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("login.invalidinfo", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void usuarioNoValido() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("login.invaliduser", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void tokenNoValido() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("login.twofactor.incorrect", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void invalidOptionApp() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("selection.invalidapp", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void invalidOptionDb() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("selection.invaliddb", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void databaseWasNotFound() {

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("db.notfound", "connection"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void plantillaNoValida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.plantilladatosvacios", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void archivoSubidoOk() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("bpo.archivosubidook", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorGuardarEstructuraPlantilla(final String error) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.error", "srvdsk"), error);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void estructuraPlantillaGuardadaOK() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("bpo.estructuraplantillaguardada", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorSubirArchivo(final String error) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), error);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorEliminarPlantilla() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.erroreliminarplantilla", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void plantillaEliminadaOk() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("bpo.plantillaeliminada", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorEliminarArchivo() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.erroreliminararchivo", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void archivoEliminadoOK() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("bpo.archivoeliminadook", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorConexionMongo() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.errorconexionmongo", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void infoResultadoProcesamiento(final String descripcion) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), descripcion);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorRepetidosMongo() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.errorrepetidosmongo", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void errorNoPlantillaPublicada() {
        System.out.println("errorNoPlantillaPublicada()");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, obtenerTag("mensaje.error", "srvdsk"), obtenerTag("bpo.errorplantillanopublicada", "bpo"));
        FacesContext.getCurrentInstance().addMessage(null, msg);



    }


    public static void ningunaSeleccion() {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerTag("mensaje.advertencia", "srvdsk"), obtenerTag("mensaje.organizacion", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }


    public static void asignadoReportado() {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.reportado", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static void asignadoIncidenteMayor() {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, obtenerTag("mensaje.info", "srvdsk"), obtenerTag("mensaje.incidente", "srvdsk"));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static String obtenerTag(String llave, String modulo) {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, modulo);
            return bundle.getString(llave);
        }
        catch (Exception e){
            return "";
        }
    }
}
