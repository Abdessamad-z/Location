package Location.Models;

import Location.Classes.Contrat;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class ContratModel {

    public ContratModel(){}

    public void add(Contrat contrat) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Insert into contrat (dateEcheance,signé,codeReservation) values (?,0,?);" +
                "Update reservation set etat='Validée' where codeReservation=?");
        statement.setDate(1,contrat.getDateEcheance());
        statement.setInt(2,contrat.getIdReservation());
        statement.setInt(3,contrat.getIdReservation());
        statement.executeUpdate();

    }

    public void update(Contrat contrat) throws IOException, SQLException {
        PreparedStatement statement=Database.getStatement ("Update reservation set etat='Non validée' where codeReservation=(select " +
                "codeReservation from contrat where numContrat=?);"+
                "Update contrat set dateEcheance=?, dateRestitution=?, codeReservation=? where numContrat=?;"+
                "Update reservation set etat='Validée' where codeReservation=?;");
        statement.setInt(1,contrat.getId());
        statement.setDate(2,contrat.getDateEcheance());
        statement.setDate(3,contrat.getDateRestitution());
        statement.setInt(4,contrat.getIdReservation());
        statement.setInt(5,contrat.getId());
        statement.setInt(6,contrat.getIdReservation());
        statement.executeUpdate();
        if(contrat.getDateRestitution()!=null) {
            addSanction(contrat);
        }
    }

    public void delete(Contrat contrat) throws IOException, SQLException {
        PreparedStatement statement=Database.getStatement ("update facture set numContrat=null where numContrat=?;"+
                "update sanction set numContrat=null where numContrat=?;"+
                "delete from contrat where numContrat=?;"+
                "Update reservation set etat='Non validée' where codeReservation=?;");
        statement.setInt(1,contrat.getId());
        statement.setInt(2,contrat.getId());
        statement.setInt(3,contrat.getId());
        statement.setInt(4,contrat.getIdReservation());
        statement.executeUpdate();
    }

    private void addSanction(Contrat contrat) throws SQLException {
        Duration duration = Duration.between(contrat.getDateEcheance().toLocalDate().atStartOfDay(),contrat.getDateRestitution().toLocalDate().atStartOfDay());
        double diff = duration.toDays();
        if(diff>0){
            PreparedStatement checkStatement = Database.getStatement("Select * from sanction where numContrat=?");
            checkStatement.setInt(1,contrat.getId());
            ResultSet resultSet=checkStatement.executeQuery();
            PreparedStatement sectionStatement;
            if(!resultSet.next())
                sectionStatement = Database.getStatement("Insert into sanction (Montant,numContrat,enCours) values (?,?,0)");
            else
                sectionStatement = Database.getStatement("Update sanction set Montant=? where numContrat=?");
            sectionStatement.setDouble(1,diff*2000);
            sectionStatement.setInt(2,contrat.getId());
            sectionStatement.executeUpdate();
        }
    }

    public ObservableList<Contrat> allContratList() throws SQLException {
        String query="select * from contrat";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Contrat> nonSigneContratList() throws SQLException {
        String query="select * from contrat where signé=0";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Contrat> searchContrat(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from contrat where numContrat like ?");
        String pathern=key+"%";
        statement.setString(1, pathern);
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }

    private ObservableList<Contrat> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Contrat> contratList= FXCollections.observableArrayList();
        Contrat contrat;
        while(rs.next()){
            contrat=new Contrat(rs.getInt(1),rs.getDate(2),rs.getDate(3),
                    rs.getDate(4),rs.getBoolean(5),rs.getInt(6));
            contratList.add(contrat);
        }
        return contratList;
    }
}
