package Location.Controllers;

import Location.Classes.Client;
import Location.Config.AlertBox;
import Location.Models.ClientModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientSelectorController implements Initializable {

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> colId;

    @FXML
    private TableColumn<Client, String> colName;

    @FXML
    private TableColumn<Client, String> colNum;

    @FXML
    private TableColumn<Client, String> colAdress;

    @FXML
    private TextField search;

    private Integer selectedClientId;

    private ClientModel clientModel = new ClientModel();

    static public Integer clientId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientId = null;
        try {
            showClients(clientModel.allClientList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showClients(ObservableList<Client> clientList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("gsm"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        clientTable.setItems(clientList);
    }

    public void btnAction(ActionEvent event) throws SQLException, IOException {
        if (event.getSource() == btnConfirm) {
            confirm();
        }
        if (event.getSource() == btnCancel) {
            cancel();
        }
    }

    void cancel() {
        AlertBox.stage.close();
    }

    void confirm() {
        clientId = selectedClientId;
        AlertBox.stage.close();
    }

    @FXML
    void onTableClick() {
        ObservableList<Client> clientlist = clientTable.getSelectionModel().getSelectedItems();
        if (clientlist.isEmpty())
            return;
        selectedClientId = clientlist.get(0).getId();
    }

    @FXML
    void searchClient() throws SQLException {
        String key = search.getText();
        if (key.isEmpty())
            showClients(clientModel.allClientList());
        else {
            ObservableList<Client> clientlist = clientModel.searchClient(key);
            showClients(clientlist);
        }
    }

}
