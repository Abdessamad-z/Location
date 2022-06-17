package Location.Classes;

import java.sql.Date;

public class Contrat {
    private Integer id;
    private Date dateContrat;
    private Date dateEcheance;
    private Date dateRestitution;
    private Boolean signe;
    private Integer idReservation;

    public Contrat(Integer id, Date dateContrat, Date dateEcheance,Date dateRestitution,Boolean signe, Integer idReservation) {
        this.id = id;
        this.dateContrat = dateContrat;
        this.dateEcheance = dateEcheance;
        this.dateRestitution=dateRestitution;
        this.signe=signe;
        this.idReservation = idReservation;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateContrat() {
        return dateContrat;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public Date getDateRestitution(){
        return dateRestitution;
    }

    public Boolean isSigne(){
        return signe;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

}
