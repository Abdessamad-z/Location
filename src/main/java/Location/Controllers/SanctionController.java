package Location.Controllers;

import Location.Classes.Sanction;
import Location.Models.SanctionModel;
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

public class SanctionController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnTerminate;

    @FXML
    private TableView<Sanction> sanctionTable;

    @FXML
    private TableColumn<Sanction, Integer> colContrat;

    @FXML
    private TableColumn<Sanction, Boolean> colEnCours;

    @FXML
    private TableColumn<Sanction, Integer> colId;

    @FXML
    private TableColumn<Sanction, Double> colMontant;

    @FXML
    private TextField search;

    private Sanction selectedSanction;

    private SanctionModel sanctionModel = new SanctionModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sanctionModel.updateAll();
            showSanction(sanctionModel.allSanctionList());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void showSanction(ObservableList<Sanction> sanctionList) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colEnCours.setCellValueFactory(new PropertyValueFactory<>("enCours"));
        colContrat.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        sanctionTable.setItems(sanctionList);
    }

    @FXML
    void btnAction(ActionEvent event) throws SQLException, IOException {
        if (event.getSource() == btnTerminate) {
            terminateSanction();
        }
        if (event.getSource() == btnDelete) {
            deleteSanction();
        }
    }

    private void terminateSanction() throws SQLException, IOException {
        sanctionModel.terminate(selectedSanction);
        back(true);
        showSanction(sanctionModel.allSanctionList());
    }

    private void deleteSanction() throws SQLException, IOException {
        sanctionModel.delete(selectedSanction);
        btnDelete.setDisable(true);
        btnTerminate.setDisable(true);
        showSanction(sanctionModel.allSanctionList());
    }

    @FXML
    void onTableClick() {
        ObservableList<Sanction> sanctionList = sanctionTable.getSelectionModel().getSelectedItems();
        if (sanctionList.isEmpty())
            return;
        selectedSanction = sanctionList.get(0);
        back(selectedSanction.getEnCours());
    }

    @FXML
    void searchSanction() throws SQLException {
        String key = search.getText();
        if (key.isEmpty())
            showSanction(sanctionModel.allSanctionList());
        else {
            showSanction(sanctionModel.searchSanction(key));
        }
    }

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnTerminate.setDisable(b.equals(false));
    }

}
