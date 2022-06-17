package Location.Classes;

import java.io.*;
import java.sql.*;

public class Vehicule {
    private String id;
    private String marque;
    private String modele;
    private String type;
    private String carburant;
    private Double km;
    private Date circulation;
    private Boolean dispo;
    private InputStream image;
    private Integer parkId;

    public Vehicule(String id, String marque, String modele, String type, String carburant, Double km, Date circulation, Boolean dispo, InputStream image, Integer parkId) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.type = type;
        this.carburant = carburant;
        this.km = km;
        this.circulation = circulation;
        this.dispo = dispo;
        this.image = image;
        this.parkId = parkId;
    }

    public String getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getType() {
        return type;
    }

    public String getCarburant() {
        return carburant;
    }

    public Double getKm() {
        return km;
    }

    public Date getCirculation() {
        return circulation;
    }

    public Boolean isDispo() {
        return dispo;
    }

    public InputStream getImage() {
        return image;
    }

    public Integer getParkId() {
        return parkId;
    }

}
