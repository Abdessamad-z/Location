package Location.Models;

import Location.Classes.Facture;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactureModel {

    public FactureModel(){}

    public void add(Facture facture) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Insert into facture (montant,numContrat)values (?,?);" +
                "Update contrat set signé=1 where numContrat=?");
        statement.setDouble(1,facture.getMontant());
        statement.setInt(2,facture.getIdContrat());
        statement.setInt(3,facture.getIdContrat());
        statement.executeUpdate();
    }

    public void update(Facture facture) throws IOException, SQLException {
        PreparedStatement statement=Database.getStatement ("Update contrat set signé=0 where numContrat=(select " +
                "numContrat from facture where numFacture=?);"+
                "Update facture set montant=? ,numContrat=? where numFacture=?;" +
                "Update contrat set signé=1 where numContrat=?");
        statement.setInt(1,facture.getId());
        statement.setDouble(2,facture.getMontant());
        statement.setInt(3,facture.getIdContrat());
        statement.setInt(4,facture.getId());
        statement.setInt(5,facture.getIdContrat());
        statement.executeUpdate();
    }
    public void delete(Facture facture) throws IOException, SQLException {
        PreparedStatement statement=Database.getStatement ("Delete from facture where numFacture=?;"+
                "Update contrat set signé=0 where numContrat=?");
        statement.setInt(1,facture.getId());
        statement.setInt(2,facture.getIdContrat());
        statement.executeUpdate();
    }

    public ObservableList<Facture> searchFacture(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from facture where numFacture like ?");
        String pathern=key+"%";
        statement.setString(1, pathern);
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }



    public ObservableList<Facture> allFactureList() throws SQLException {
        String query="select * from facture";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    private ObservableList<Facture> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Facture> factureList= FXCollections.observableArrayList();
        Facture facture;
        while(rs.next()){
            facture=new Facture(rs.getInt(1),rs.getDate(2),rs.getDouble(3),rs.getInt(4));
            factureList.add(facture);
        }
        return factureList;
    }
}
