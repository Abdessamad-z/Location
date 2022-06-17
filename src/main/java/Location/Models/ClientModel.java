package Location.Models;

import Location.Config.Database;
import Location.Classes.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientModel {

    public ClientModel() {}
    public void addClient(Client client) throws SQLException, IOException {
        PreparedStatement statement=Database.getStatement("Insert into client (nomComplet,numGSM,adresse,permis) values (?,?,?,?)");
        statement.setString(1,client.getName());
        statement.setString(2,"0"+client.getGsm());
        statement.setString(3,client.getAdress());
        statement.setBinaryStream(4,client.getPermis(),(client.getPermis()!=null)?client.getPermis().available():0);
        statement.executeUpdate();
    }

    public void updateClient(Client client) throws SQLException, IOException {
        PreparedStatement statement=Database.getStatement("Update client set nomComplet=?,numGSM=?,adresse=? ,permis=? where codeClient=?");
        statement.setString(1,client.getName());
        statement.setString(2,"0"+client.getGsm());
        statement.setString(3,client.getAdress());
        statement.setBinaryStream(4,client.getPermis(),(client.getPermis()!=null)?client.getPermis().available():0);
        statement.setInt(5,client.getId());
        statement.executeUpdate();
    }
    public void deleteClient(Client client) throws SQLException {
        PreparedStatement statement=Database.getStatement("update Reservation set codeClient=null where codeClient=?;"+
                "Delete from client where codeClient=?");
        statement.setInt(1,client.getId());
        statement.setInt(2,client.getId());
        statement.executeUpdate();
    }

    public ObservableList<Client> searchClient(String key) throws SQLException {
        PreparedStatement statement=Database.getStatement("Select * from client where codeClient like ? or nomComplet like ? or" +
                " adresse like ? or numGSM like ?");
        String pathern="%"+key+"%";
        for(int i=1;i<5; i++){
            statement.setString(i, pathern);
        }
        ResultSet rs=statement.executeQuery();
        return rsToList(rs);
    }

    public ObservableList<Client> allClientList() throws SQLException {
        String query="select * from client";
        ResultSet rs=Database.ExecureQ(query);
        return rsToList(rs);
    }

    private ObservableList<Client> rsToList(ResultSet rs) throws SQLException {
        ObservableList<Client> clientList= FXCollections.observableArrayList();
        Client client;
        while(rs.next()){
            client=new Client(rs.getInt(1),rs.getString(2),
                    rs.getString(4),rs.getString(3),rs.getBinaryStream(5));
            clientList.add(client);
        }
        return clientList;
    }
}
