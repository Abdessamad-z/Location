package Location.Classes;

import java.sql.Date;

public class Reservation {
    private Integer code;
    private Date dateReservation;
    private Date dateDepart;
    private Date dateRetour;
    private String etat;
    private Integer codeClient;
    private String matricule;

    public Reservation(Integer code, Date dateReservation, Date dateDepart, Date dateRetour, String etat, Integer codeClient, String matricule) {
        this.code = code;
        this.dateReservation = dateReservation;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.etat = etat;
        this.codeClient = codeClient;
        this.matricule = matricule;
    }

    public Integer getCode() {
        return code;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public String getEtat() {
        return etat;
    }

    public Integer getCodeClient() {
        return codeClient;
    }

    public String getMatricule() {
        return matricule;
    }


}
