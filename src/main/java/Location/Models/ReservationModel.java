package Location.Models;

import Location.Classes.Reservation;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationModel {

    public ReservationModel(){}

    public void add(Reservation reservation) throws SQLException {
        PreparedStatement statement= Database.getStatement("Update vehicule set disponibilité=0 where numImmatruculation=?; "+
                "Insert into reservation (dateDepart,dateRetour,codeClient,numImmatruculation) values (?,?,?,?)");
        statement.setString(1,reservation.getMatricule());
        statement.setDate(2,reservation.getDateDepart());
        statement.setDate(3,reservation.getDateRetour());
        statement.setInt(4,reservation.getCodeClient());
        statement.setString(5,reservation.getMatricule());
        statement.executeUpdate();
    }

    public void update(Reservation reservation) throws SQLException {
        PreparedStatement statement=Database.getStatement("Update vehicule set disponibilité=1 where numImmatruculation=(select " +
                        "numImmatruculation from Reservation where codeReservation=?);"+
                "update reservation set dateDepart=?,dateRetour=?,etat=?,codeClient=?, numImmatruculation=? where codeReservation=?;"+
                "Update vehicule set disponibilité=0 where numImmatruculation=?; ");
        statement.setInt(1,reservation.getCode());
        statement.setDate(2,reservation.getDateDepart());
        statement.setDate(3,reservation.getDateRetour());
        statement.setString(4,reservation.getEtat());
        statement.setInt(5,reservation.getCodeClient());
        statement.setString(6,reservation.getMatricule());
        statement.setInt(7,reservation.getCode());
        statement.setString(8,reservation.getMatricule());
        statement.executeUpdate();
    }

    public void delete(Reservation reservation) throws SQLException {
        PreparedStatement statement=Database.getStatement("update contrat set codeReservation=null where codeReservation=?;"+
                "delete from reservation where codeReservation=?; "+
                "Update vehicule set disponibilité=1 where numImmatruculation=?");
        statement.setInt(1,reservation.getCode());
        statement.setInt(2,reservation.getCode());
        statement.setString(3,reservation.getMatricule());
        statement.executeUpdate();
    }

    public void updateAll() throws SQLException {
        Database.ExecureUp("update reservation set etat='Annulée' where timestampdiff(DAY,(CURRENT_DATE),dateDepart)<2");
    }

    public ObservableList<Reservation> searchReservation(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from reservation where codeReservation like ? or etat like ?");
        String pathern=key+"%";
        for(int i=1;i<3; i++){
            statement.setString(i, pathern);
        }
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }

    public ObservableList<Reservation> allReservationList(String etat) throws SQLException {
        String query;
        if(etat.equals("all"))
            query="select * from reservation";
        else
            query="select * from reservation where etat='"+etat+"'";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    private ObservableList<Reservation> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Reservation> reservationList= FXCollections.observableArrayList();
        Reservation reservation;
        while(rs.next()){
            reservation=new Reservation(rs.getInt(1),rs.getDate(2),rs.getDate(3),
                    rs.getDate(4),rs.getString(5),rs.getInt(6),rs.getString(7));
            reservationList.add(reservation);
        }
        return reservationList;
    }

}
