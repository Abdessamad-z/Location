package Location.Classes;

import java.sql.Date;

public class Facture {
    private Integer id;
    private Date dateFacture;
    private Double montant;
    private Integer idContrat;

    public Facture(Integer id, Date dateFacture,Double montant, Integer idContrat) {
        this.id = id;
        this.dateFacture = dateFacture;
        this.montant = montant;
        this.idContrat = idContrat;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public Double getMontant() {
        return montant;
    }

    public Integer getIdContrat() {
        return idContrat;
    }


}
