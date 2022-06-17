package Location.Classes;

public class Parking {
    private Integer id;
    private Integer capacity;
    private String rue;
    private String arrondissment;

    public Parking(Integer id, Integer capacity, String rue, String arrondissment) {
        this.id = id;
        this.capacity = capacity;
        this.rue = rue;
        this.arrondissment = arrondissment;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getRue() {
        return rue;
    }

    public String getArrondissment() {
        return arrondissment;
    }


}
