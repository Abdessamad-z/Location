package Location.Classes;

public class Utilisateur {
    private String id;
    private String pwd;
    private String name;
    private String adress;
    private String num;
    private Boolean admin;

    public Utilisateur(String id, String pwd, String name, String adress, String num, Boolean admin) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.adress = adress;
        this.num = num;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getNum() {
        return num;
    }

    public Boolean getAdmin() {
        return admin;
    }


}
