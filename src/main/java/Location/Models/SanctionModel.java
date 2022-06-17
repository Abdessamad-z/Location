package Location.Models;

import Location.Classes.Sanction;
import Location.Config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

public class SanctionModel {

    public SanctionModel(){}

    public void terminate(Sanction sanction) throws IOException, SQLException {
        PreparedStatement statement= Database.getStatement ("Update Contrat set dateRestitution=(CURRENT_DATE) where numContrat=?;" +
                "Update Sanction set enCours=0 where numSanction=?");
        statement.setInt(1,sanction.getIdContrat());
        statement.setInt(2,sanction.getId());
        statement.executeUpdate();
    }

    public void delete(Sanction sanction) throws IOException, SQLException {
        PreparedStatement statement=Database.getStatement ("Delete from Sanction where numSanction=?");
        statement.setInt(1,sanction.getId());
        statement.executeUpdate();
    }

    public void updateAll() throws IOException, SQLException {
        String query=("select numContrat,dateEcheance from contrat where dateRestitution is null");
        ResultSet resultSet=Database.ExecureQ(query);
        PreparedStatement Statement;
        while(resultSet.next()){
            Duration duration = Duration.between(resultSet.getDate(2).toLocalDate().atStartOfDay(),
                    LocalDate.now().atStartOfDay());
            double diff=duration.toDays();
            if(diff<0)
                continue;
            PreparedStatement checkStatement = Database.getStatement("Select * from sanction where numContrat=?");
            checkStatement.setInt(1,resultSet.getInt(1));
            ResultSet resultSet2=checkStatement.executeQuery();
            if(!resultSet2.next())
                Statement = Database.getStatement("Insert into sanction (Montant,numContrat,enCours) values (?,?,1)");
            else
                Statement = Database.getStatement("Update sanction set Montant=? where numContrat=?");
            Statement.setDouble(1,diff*2000);
            Statement.setInt(2,resultSet.getInt(1));
            Statement.executeUpdate();
        }
    }

    public ObservableList<Sanction> allSanctionList() throws SQLException {
        String query="select * from sanction";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    public ObservableList<Sanction> searchSanction(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from Sanction where numSanction like ?");
        String pathern=key+"%";
        statement.setString(1, pathern);
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }

    private ObservableList<Sanction> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Sanction> sanctionList= FXCollections.observableArrayList();
        Sanction sanction;
        while(rs.next()){
            sanction=new Sanction(rs.getInt(1),rs.getDouble(2),rs.getBoolean(3),rs.getInt(4));
            sanctionList.add(sanction);
        }
        return sanctionList;
    }
}
