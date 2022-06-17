package Location.Models;

import Location.Classes.Utilisateur;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurModel {
    public static String currentUser;

    public UtilisateurModel(){}

    public void add(Utilisateur utilisateur) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Insert into utilisateur (identifiant,motDePasse,nomComplet,adresse,numGSM,isAdmin) " +
                "values (?,?,?,?,?,?)");
        statement.setString(1,utilisateur.getId());
        statement.setString(2,utilisateur.getPwd());
        statement.setString(3,utilisateur.getName());
        statement.setString(4,utilisateur.getAdress());
        statement.setString(5,"0"+utilisateur.getNum());
        statement.setBoolean(6,utilisateur.getAdmin());
        statement.executeUpdate();
    }
    public void update(Utilisateur utilisateur) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Update utilisateur set motDePasse=?, nomComplet=?, adresse=?," +
                "numGSM=?, isAdmin=? where identifiant=?");
        statement.setString(1,utilisateur.getPwd());
        statement.setString(2,utilisateur.getName());
        statement.setString(3,utilisateur.getAdress());
        statement.setString(4,"0"+utilisateur.getNum());
        statement.setBoolean(5,utilisateur.getAdmin());
        statement.setString(6,utilisateur.getId());
        statement.executeUpdate();
    }
    public void delete(Utilisateur utilisateur) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Delete from utilisateur where identifiant=?");
        statement.setString(1,utilisateur.getId());
        statement.executeUpdate();
    }

    public Utilisateur check(String id, String pwd) throws SQLException {
        PreparedStatement statement= Database.getStatement ("select * from utilisateur where identifiant=? and motDePasse=?");
        statement.setString(1,id);
        statement.setString(2,pwd);
        ResultSet rs=statement.executeQuery();
        if(rs.next())
            return new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getBoolean(6));
        return null;
    }

    public boolean checkId(String id) throws SQLException {
        PreparedStatement statement= Database.getStatement ("select * from utilisateur where identifiant=?");
        statement.setString(1,id);
        ResultSet rs=statement.executeQuery();
        if(rs.next())
            return true;
        return false;
    }

    public ObservableList<Utilisateur> searchUtilisateur(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from utilisateur where identifiant like ? or motDePasse like ? or nomComplet like ? or adresse like ? or numGSM like ?" );
        String pathern="%"+key+"%";
        for(int i=1;i<6; i++){
            statement.setString(i, pathern);
        }
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }


    public ObservableList<Utilisateur> allUtilisateurList() throws SQLException {
        ObservableList<Utilisateur> utilisateurList= FXCollections.observableArrayList();
        String query="select * from utilisateur";
        ResultSet rs=Database.ExecureQ(query);
        Utilisateur utilisateur;
        while(rs.next()){
            utilisateur=new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getBoolean(6));
            utilisateurList.add(utilisateur);
        }
        return utilisateurList;
    }

    private ObservableList<Utilisateur> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Utilisateur> utilisateurList= FXCollections.observableArrayList();
        Utilisateur utilisateur;
        while(rs.next()){
            utilisateur=new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getBoolean(6));
            utilisateurList.add(utilisateur);
        }
        return utilisateurList;
    }
}
