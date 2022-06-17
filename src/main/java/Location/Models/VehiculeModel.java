package Location.Models;

import Location.Classes.Vehicule;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculeModel {

    public VehiculeModel(){}

    public void add(Vehicule vehicule) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Insert into vehicule (numImmatruculation,marque,modele,type," +
                "carburant,compteurKM,dateMiseEnCirculation,Disponibilité,image) values (?,?,?,?,?,?,?,?,?)");
        statement.setString(1,vehicule.getId());
        statement.setString(2,vehicule.getMarque());
        statement.setString(3,vehicule.getModele());
        statement.setString(4,vehicule.getType());
        statement.setString(5,vehicule.getCarburant());
        statement.setDouble(6,vehicule.getKm());
        statement.setDate(7,vehicule.getCirculation());
        statement.setBoolean(8,vehicule.isDispo());
        statement.setBinaryStream(9,vehicule.getImage(),(vehicule.getImage()!=null)?vehicule.getImage().available():0);
        statement.executeUpdate();

    }

    public void update(Vehicule vehicule) throws SQLException, IOException {
        PreparedStatement statement=Database.getStatement("Update vehicule set marque=? ,modele=? ,type=? ,carburant=? ,compteurKM=? ," +
                "dateMiseEnCirculation=? ,disponibilité=? ,image=? where numImmatruculation=?");
        statement.setString(9,vehicule.getId());
        statement.setString(1,vehicule.getMarque());
        statement.setString(2,vehicule.getModele());
        statement.setString(3,vehicule.getType());
        statement.setString(4,vehicule.getCarburant());
        statement.setDouble(5,vehicule.getKm());
        statement.setDate(6,vehicule.getCirculation());
        statement.setBoolean(7,vehicule.isDispo());
        statement.setBinaryStream(8, vehicule.getImage(), (vehicule.getImage()!=null)?vehicule.getImage().available():0);
        statement.executeUpdate();
    }

    public void toParking(Vehicule vehicule, int num) throws SQLException {
        PreparedStatement statement=Database.getStatement("Update vehicule set numParking=? where numImmatruculation=?");
        statement.setInt(1,num);
        statement.setString(2,vehicule.getId());
        statement.executeUpdate();
    }

    public void revoke(Vehicule vehicule) throws SQLException {
        PreparedStatement statement=Database.getStatement("Update vehicule set numParking=null where numImmatruculation=?");
        statement.setString(1,vehicule.getId());
        statement.executeUpdate();
    }

    public void delete(Vehicule vehicule) throws SQLException {
        PreparedStatement statement=Database.getStatement("update reservation set numImmatruculation=null where numImmatruculation=?;"+
                "Delete from vehicule where numImmatruculation=?");
        statement.setString(1,vehicule.getId());
        statement.setString(2,vehicule.getId());
        statement.executeUpdate();
    }

    public void updateDispo() throws SQLException, IOException {
        Database.ExecureUp("Update vehicule set disponibilité=1 where numImmatruculation in (select numImmatruculation" +
                " from reservation where etat='Annulée')");
    }

    public ObservableList<Vehicule> searchVehicule(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from vehicule where marque like ? or modele like ? or type like ? or carburant like ? or" +
        " numImmatruculation like ?");
        String pathern="%"+key+"%";
        for(int i=1;i<6; i++){
            statement.setString(i, pathern);
        }
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }


    public ObservableList<Vehicule> allVehiculeList() throws SQLException {
        String query="select * from vehicule";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Vehicule> freeVehiculeList() throws SQLException {
        String query = "select * from vehicule where numParking is null";
        ResultSet rs = Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Vehicule> dispoVehiculeList() throws SQLException {
        String query="select * from vehicule where disponibilité=1";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    private ObservableList<Vehicule> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Vehicule> vehiculeList= FXCollections.observableArrayList();
        Vehicule vehicule;
        while(rs.next()){
            vehicule=new Vehicule(rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getDouble(6),
                    rs.getDate(7),rs.getBoolean(8), rs.getBinaryStream(9),rs.getInt(10));
            vehiculeList.add(vehicule);
        }
        return vehiculeList;
    }
}
