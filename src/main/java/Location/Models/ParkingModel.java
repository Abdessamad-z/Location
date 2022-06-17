package Location.Models;

import Location.Classes.Parking;
import Location.Classes.Vehicule;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingModel {

    public ParkingModel(){}
    public void add(Parking parking) throws SQLException {
        PreparedStatement statement=Database.getStatement("Insert into parking (capacite,rue,arrondissement) values (?,?,?)");
        statement.setInt(1,parking.getCapacity());
        statement.setString(2,parking.getRue());
        statement.setString(3,parking.getArrondissment());
        statement.executeUpdate();
    }

    public void update(Parking parking) throws SQLException {
        PreparedStatement statement=Database.getStatement("Update parking set capacite=?,rue=?,arrondissement=? where numParking=?");
        statement.setInt(1,parking.getCapacity());
        statement.setString(2,parking.getRue());
        statement.setString(3,parking.getArrondissment());
        statement.setInt(4,parking.getId());
        statement.executeUpdate();
    }
    public void delete(Parking parking) throws SQLException {
        PreparedStatement statement=Database.getStatement("update vehicule set numParking=null where numParking=?;"+
                "Delete from parking where numParking=?");
        statement.setInt(1,parking.getId());
        statement.setInt(2,parking.getId());
        statement.executeUpdate();
    }
    public ObservableList<Parking> allParkingList() throws SQLException {
        String query="select * from parking";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Vehicule> parkingVehiculeList(Parking parking) throws SQLException {
        ObservableList<Vehicule> vehiculeList= FXCollections.observableArrayList();
        PreparedStatement statement=Database.getStatement("select vehicule.* from vehicule,parking where " +
                "parking.numParking=vehicule.numParking=?");
        statement.setInt(1,parking.getId());
        ResultSet rs=statement.executeQuery();
        Vehicule vehicule;
        while(rs.next()){
            vehicule=new Vehicule(rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6),
                    rs.getDate(7),rs.getBoolean(8),null,rs.getInt(10));
            vehiculeList.add(vehicule);
        }
        return vehiculeList;
    }

    public ObservableList<Parking> searchParking(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from parking where numParking like ? or rue like ? or arrondissement like ?");
        String pathern="%"+key+"%";
        for(int i=2;i<4; i++){
            statement.setString(i, pathern);
        }
        statement.setString(1,key+"%");
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }

    private ObservableList<Parking> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Parking> parkingList= FXCollections.observableArrayList();
        Parking parking;
        while(rs.next()){
            parking=new Parking(rs.getInt(1),rs.getInt(2),
                    rs.getString(3),rs.getString(4));
            parkingList.add(parking);
        }
        return parkingList;
    }
}
