package Location.Controllers;

import Location.Classes.Contrat;
import Location.Config.AlertBox;
import Location.Models.ContratModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContratSelectorController implements Initializable {

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Contrat> contratTable;

    @FXML
    private TableColumn<Contrat, Date> colDateContrat;

    @FXML
    private TableColumn<Contrat, Date> colDateEcheance;

    @FXML
    private TableColumn<Contrat, Date> colDateRestitution;

    @FXML
    private TableColumn<Contrat, Integer> colId;

    @FXML
    private TableColumn<Contrat, Integer> colReseration;

    @FXML
    private TextField search;

    private Integer selectedContratId;

    static public Integer contratId;

    private ContratModel contratModel = new ContratModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showContrats(contratModel.nonSigneContratList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showContrats(ObservableList<Contrat> contratList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateContrat.setCellValueFactory(new PropertyValueFactory<>("dateContrat"));
        colDateEcheance.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        colDateRestitution.setCellValueFactory(new PropertyValueFactory<>("dateRestitution"));
        colReseration.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        contratTable.setItems(contratList);
    }

    @FXML
    void btnAction(ActionEvent event) throws SQLException, IOException {
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
        contratId = selectedContratId;
        AlertBox.stage.close();
    }

    @FXML
    void onTableClick() {
        ObservableList<Contrat> contratList = contratTable.getSelectionModel().getSelectedItems();
        if (contratList.isEmpty())
            return;
        selectedContratId = contratList.get(0).getId();
    }

    @FXML
    void searchContrat() throws SQLException {
        String key = search.getText();
        if (key.isEmpty())
            showContrats(contratModel.allContratList());
        else {
            showContrats(contratModel.searchContrat(key));
        }
    }

}
