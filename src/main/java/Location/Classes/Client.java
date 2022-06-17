package Location.Classes;

import java.io.InputStream;

public class Client {
    private Integer id;
    private String name;
    private String gsm;
    private String adress;
    private InputStream permis;

    public Client(Integer id,String name, String gsm, String adress,InputStream permis) {
        this.id = id;
        this.name = name;
        this.gsm = gsm;
        this.adress = adress;
        this.permis=permis;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGsm() {
        return gsm;
    }

    public String getAdress() {
        return adress;
    }

    public InputStream getPermis() {
        return permis;
    }
}
