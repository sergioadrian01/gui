package com.axa.control.views.core;

import com.axa.control.controllers.commons.SessionManager;
import com.axa.control.enums.SessionObjects;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;


@ManagedBean(name = "facesLayout")
@ViewScoped
public class FacesLayout implements Serializable {

    private SessionManager sessionManager = new SessionManager();

    private String northWest = "/blank.xhtml";
    private String north = "/modulo/menus/menu-principal.xhtml";
    private String northEast = "/blank.xhtml";

    private String westNorth = "/blank.xhtml";
    private String west = "/blank.xhtml";
    private String westSouth = "/blank.xhtml";

    private String centerNorth = "/modulo/menus/menu-secundario.xhtml";
    private String centerWest = "/blank.xhtml";
    private String center = "/modulo/usuario/dashboard.xhtml";
    private String centerEast = "/blank.xhtml";
    private String centerSouth = "/blank.xhtml";

    private String eastNorth = "/blank.xhtml";
    private String east = "/blank.xhtml";
    private String eastSouth = "/blank.xhtml";

    private String southWest = "/blank.xhtml";
    private String south = "/blank.xhtml";
    private String southEast = "/blank.xhtml";


    public void renderContent(ActionEvent event) {

        if (event.getComponent().getAttributes().get("northwest") != null) {
            this.northWest = (String) event.getComponent().getAttributes().get("northwest");
            sessionManager.setObject(SessionObjects.NORTHWEST, this.northWest);
        }

        if (event.getComponent().getAttributes().get("north") != null) {
            this.north = (String) event.getComponent().getAttributes().get("north");
            sessionManager.setObject(SessionObjects.NORTH, this.north);
        }

        if (event.getComponent().getAttributes().get("northeast") != null) {
            this.northEast = (String) event.getComponent().getAttributes().get("northeast");
            sessionManager.setObject(SessionObjects.NORTHEAST, this.northEast);
        }

        if (event.getComponent().getAttributes().get("westnorth") != null) {
            this.westNorth = (String) event.getComponent().getAttributes().get("westnorth");
            sessionManager.setObject(SessionObjects.WESTNORTH, this.westNorth);
        }

        if (event.getComponent().getAttributes().get("west") != null) {
            this.west = (String) event.getComponent().getAttributes().get("west");
            sessionManager.setObject(SessionObjects.WEST, this.west);
        }

        if (event.getComponent().getAttributes().get("westsouth") != null) {
            this.westSouth = (String) event.getComponent().getAttributes().get("westsouth");
            sessionManager.setObject(SessionObjects.WESTSOUTH, this.westSouth);
        }

        if (event.getComponent().getAttributes().get("centernorth") != null) {
            this.centerNorth = (String) event.getComponent().getAttributes().get("centernorth");
            sessionManager.setObject(SessionObjects.CENTERNORTH, this.centerNorth);
        }

        if (event.getComponent().getAttributes().get("centerwest") != null) {
            this.centerWest = (String) event.getComponent().getAttributes().get("centerwest");
            sessionManager.setObject(SessionObjects.CENTERWEST, this.centerWest);
        }

        if (event.getComponent().getAttributes().get("center") != null) {
            this.center = (String) event.getComponent().getAttributes().get("center");
            sessionManager.setObject(SessionObjects.CENTER, this.center);
        }

        if (event.getComponent().getAttributes().get("centereast") != null) {
            this.centerEast = (String) event.getComponent().getAttributes().get("centereast");
            sessionManager.setObject(SessionObjects.CENTEREAST, this.centerEast);
        }

        if (event.getComponent().getAttributes().get("centersouth") != null) {
            this.centerSouth = (String) event.getComponent().getAttributes().get("centersouth");
            sessionManager.setObject(SessionObjects.CENTERSOUTH, this.centerSouth);
        }

        if (event.getComponent().getAttributes().get("eastnorth") != null) {
            this.eastNorth = (String) event.getComponent().getAttributes().get("eastnorth");
            sessionManager.setObject(SessionObjects.EASTNORTH, this.eastNorth);
        }

        if (event.getComponent().getAttributes().get("east") != null) {
            this.east = (String) event.getComponent().getAttributes().get("east");
            sessionManager.setObject(SessionObjects.EAST, this.east);
        }

        if (event.getComponent().getAttributes().get("eastsouth") != null) {
            this.eastSouth = (String) event.getComponent().getAttributes().get("eastsouth");
            sessionManager.setObject(SessionObjects.EASTSOUTH, this.eastSouth);
        }

        if (event.getComponent().getAttributes().get("southwest") != null) {
            this.southWest = (String) event.getComponent().getAttributes().get("southwest");
            sessionManager.setObject(SessionObjects.SOUTHWEST, this.southWest);
        }

        if (event.getComponent().getAttributes().get("south") != null) {
            this.south = (String) event.getComponent().getAttributes().get("south");
            sessionManager.setObject(SessionObjects.SOUTH, this.south);
        }

        if (event.getComponent().getAttributes().get("southeast") != null) {
            this.southEast = (String) event.getComponent().getAttributes().get("southeast");
            sessionManager.setObject(SessionObjects.SOUTHEAST, this.southEast);
        }

    }


    public void renderContentExtraAttr(ActionEvent event) {

        if (event.getComponent().getAttributes().get("center") != null) {
            this.center = (String) event.getComponent().getAttributes().get("center");
            sessionManager.setObject(SessionObjects.CENTER, this.center);
        }

        if (event.getComponent().getAttributes().get("notificacion") != null) {
            String notificacion = (String) event.getComponent().getAttributes().get("notificacion");
            sessionManager.setObject(SessionObjects.NOTIFICATION, notificacion);
        }

    }


    public String getNorthWest() {
        if (sessionManager.getObject(SessionObjects.NORTHWEST) != null) {
            northWest = (String) sessionManager.getObject(SessionObjects.NORTHWEST);
        }
        return northWest;
    }

    public void setNorthWest(String northWest) {
        sessionManager.setObject(SessionObjects.NORTHWEST, northWest);
        this.northWest = northWest;
    }

    public String getNorth() {

        if (sessionManager.getObject(SessionObjects.NORTH) != null) {
            north = (String) sessionManager.getObject(SessionObjects.NORTH);
        }
        return north;
    }

    public void setNorth(String north) {
        sessionManager.setObject(SessionObjects.NORTH, north);
        this.north = north;
    }

    public String getNorthEast() {
        if (sessionManager.getObject(SessionObjects.NORTHEAST) != null) {
            northEast = (String) sessionManager.getObject(SessionObjects.NORTHEAST);
        }
        return northEast;
    }

    public void setNorthEast(String northEast) {
        sessionManager.setObject(SessionObjects.NORTHEAST, northEast);
        this.northEast = northEast;
    }

    public String getWestNorth() {
        if (sessionManager.getObject(SessionObjects.WESTNORTH) != null) {
            westNorth = (String) sessionManager.getObject(SessionObjects.WESTNORTH);
        }
        return westNorth;
    }

    public void setWestNorth(String westNorth) {
        sessionManager.setObject(SessionObjects.WESTNORTH, westNorth);
        this.westNorth = westNorth;
    }

    public String getWest() {
        if (sessionManager.getObject(SessionObjects.WEST) != null) {
            west = (String) sessionManager.getObject(SessionObjects.WEST);
        }
        return west;
    }

    public void setWest(String west) {
        sessionManager.setObject(SessionObjects.WEST, west);
        this.west = west;
    }

    public String getWestSouth() {
        if (sessionManager.getObject(SessionObjects.WESTSOUTH) != null) {
            westSouth = (String) sessionManager.getObject(SessionObjects.WESTSOUTH);
        }
        return westSouth;
    }

    public void setWestSouth(String westSouth) {
        sessionManager.setObject(SessionObjects.WESTSOUTH, westSouth);
        this.westSouth = westSouth;
    }

    public String getCenterNorth() {
        if (sessionManager.getObject(SessionObjects.CENTERNORTH) != null) {
            centerNorth = (String) sessionManager.getObject(SessionObjects.CENTERNORTH);
        }
        return centerNorth;
    }

    public void setCenterNorth(String centerNorth) {
        sessionManager.setObject(SessionObjects.CENTERNORTH, centerNorth);
        this.centerNorth = centerNorth;
    }

    public String getCenterWest() {
        if (sessionManager.getObject(SessionObjects.CENTERWEST) != null) {
            centerWest = (String) sessionManager.getObject(SessionObjects.CENTERWEST);
        }
        return centerWest;
    }

    public void setCenterWest(String centerWest) {
        sessionManager.setObject(SessionObjects.CENTERWEST, centerWest);
        this.centerWest = centerWest;
    }

    public String getCenter() {

        if (sessionManager.getObject(SessionObjects.CENTER) != null) {
            center = (String) sessionManager.getObject(SessionObjects.CENTER);
        }
        return center;
    }

    public void setCenter(String center) {
        sessionManager.setObject(SessionObjects.CENTER, center);
        this.center = center;
    }

    public String getCenterEast() {
        if (sessionManager.getObject(SessionObjects.CENTEREAST) != null) {
            centerEast = (String) sessionManager.getObject(SessionObjects.CENTEREAST);
        }
        return centerEast;
    }

    public void setCenterEast(String centerEast) {
        sessionManager.setObject(SessionObjects.CENTEREAST, centerEast);
        this.centerEast = centerEast;
    }

    public String getCenterSouth() {
        if (sessionManager.getObject(SessionObjects.CENTERSOUTH) != null) {
            centerSouth = (String) sessionManager.getObject(SessionObjects.CENTERSOUTH);
        }
        return centerSouth;
    }

    public void setCenterSouth(String centerSouth) {
        sessionManager.setObject(SessionObjects.CENTERSOUTH, centerSouth);
        this.centerSouth = centerSouth;
    }

    public String getEastNorth() {
        if (sessionManager.getObject(SessionObjects.EASTNORTH) != null) {
            eastNorth = (String) sessionManager.getObject(SessionObjects.EASTNORTH);
        }
        return eastNorth;
    }

    public void setEastNorth(String eastNorth) {
        sessionManager.setObject(SessionObjects.EASTNORTH, eastNorth);
        this.eastNorth = eastNorth;
    }

    public String getEast() {
        if (sessionManager.getObject(SessionObjects.EAST) != null) {
            east = (String) sessionManager.getObject(SessionObjects.EAST);
        }
        return east;
    }

    public void setEast(String east) {
        sessionManager.setObject(SessionObjects.EAST, east);
        this.east = east;
    }

    public String getEastSouth() {
        if (sessionManager.getObject(SessionObjects.EASTSOUTH) != null) {
            eastSouth = (String) sessionManager.getObject(SessionObjects.EASTSOUTH);
        }
        return eastSouth;
    }

    public void setEastSouth(String eastSouth) {
        sessionManager.setObject(SessionObjects.EASTSOUTH, eastSouth);
        this.eastSouth = eastSouth;
    }

    public String getSouthWest() {
        if (sessionManager.getObject(SessionObjects.SOUTHWEST) != null) {
            southWest = (String) sessionManager.getObject(SessionObjects.SOUTHWEST);
        }
        return southWest;
    }

    public void setSouthWest(String southWest) {
        sessionManager.setObject(SessionObjects.SOUTHWEST, southWest);
        this.southWest = southWest;
    }

    public String getSouth() {
        if (sessionManager.getObject(SessionObjects.SOUTH) != null) {
            south = (String) sessionManager.getObject(SessionObjects.SOUTH);
        }
        return south;
    }

    public void setSouth(String south) {
        sessionManager.setObject(SessionObjects.SOUTH, south);
        this.south = south;
    }

    public String getSouthEast() {
        if (sessionManager.getObject(SessionObjects.SOUTHEAST) != null) {
            southEast = (String) sessionManager.getObject(SessionObjects.SOUTHEAST);
        }
        return southEast;
    }

    public void setSouthEast(String southEast) {
        sessionManager.setObject(SessionObjects.SOUTHEAST, southEast);
        this.southEast = southEast;
    }
}
