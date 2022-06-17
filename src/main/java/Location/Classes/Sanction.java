package Location.Classes;

public class Sanction {
    private Integer id;
    private Double montant;
    private Boolean enCours;
    private Integer idContrat;

    public Sanction(Integer id, Double montant, Boolean enCours, Integer idContrat) {
        this.id = id;
        this.montant = montant;
        this.enCours = enCours;
        this.idContrat = idContrat;
    }

    public Integer getId() {
        return id;
    }

    public Double getMontant() {
        return montant;
    }

    public Boolean getEnCours() {
        return enCours;
    }

    public Integer getIdContrat() {
        return idContrat;
    }


}
