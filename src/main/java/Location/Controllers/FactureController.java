package Location.Controllers;

import Location.Classes.Facture;
import Location.Config.AlertBox;
import Location.Models.FactureModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FactureController implements Initializable {

    @FXML
    private TextField contrat;

    @FXML
    private Button btnContrat;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Facture> factureTable;

    @FXML
    private TableColumn<Facture, Date> colDateFacture;

    @FXML
    private TableColumn<Facture, Double> colMontant;

    @FXML
    private TableColumn<Facture, Integer> colId;

    @FXML
    private TableColumn<Facture, Integer> colContrat;

    @FXML
    private TextField montant;

    @FXML
    private Label errorlog;

    @FXML
    private TextField search;

    private Facture selectedFacture;

    private FactureModel factureModel = new FactureModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showFacture(factureModel.allFactureList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showFacture(ObservableList<Facture> factureList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colContrat.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        factureTable.setItems(factureList);
    }

    @FXML
    void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted=!(montant.getText().isEmpty()||contrat.getText().isEmpty());
        if(event.getSource() == btnInsert) {
            if(areAllInserted) {
                try {
                    insertFacture();
                    errorlog.setText("");
                }
                catch(NumberFormatException e){
                    errorlog.setText("La montant doit être un nombre réel!");
                }
            }else{
                errorlog.setText("Tout les champs sont obligatoire!");
            }

        }
        if(event.getSource() == btnUpdate) {
            if(areAllInserted) {
                updateFacture();
                errorlog.setText("");
            }
            else {
                errorlog.setText("Tout les champ sont obligatoire!");
                errorlog.setTextFill(Color.web("#DF362D"));
            }
        }
        if (event.getSource() == btnDelete) {
            deleteFacture();
        }
        if (event.getSource() == btnContrat) {
            selectReservation();
        }
    }

    private void insertFacture() throws SQLException, IOException {
        Facture facture = new Facture(null, null, Double.valueOf(montant.getText()), Integer.valueOf(contrat.getText()));
        factureModel.add(facture);
        showFacture(factureModel.allFactureList());
        back(true);
        clear();
    }

    private void updateFacture() throws SQLException, IOException {
        Facture facture = new Facture(selectedFacture.getId(), null, Double.valueOf(montant.getText()), Integer.valueOf(contrat.getText()));
        factureModel.update(facture);
        showFacture(factureModel.allFactureList());
    }

    private void deleteFacture() throws SQLException, IOException {
        Facture facture = new Facture(selectedFacture.getId(), null, null, selectedFacture.getIdContrat());
        factureModel.delete(facture);
        showFacture(factureModel.allFactureList());
        back(true);
        clear();
        btnInsert.requestFocus();
    }

    private void selectReservation() throws IOException {
        AlertBox.display("ContratSelector");
        if (ContratSelectorController.contratId != null) {
            contrat.setText(String.valueOf(ContratSelectorController.contratId));
        }
    }

    @FXML
    void onTableClick() {
        errorlog.setText("");
        ObservableList<Facture> factureList = factureTable.getSelectionModel().getSelectedItems();
        if (factureList.isEmpty())
            return;
        selectedFacture = factureList.get(0);
        montant.setText(selectedFacture.getMontant().toString());
        contrat.setText(selectedFacture.getIdContrat().toString());
        back(false);
    }

    @FXML
    void searchFacture() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showFacture(factureModel.allFactureList());
        else {
            showFacture(factureModel.searchFacture(key));
        }
    }

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
    }

    private void clear() {
        montant.clear();
        contrat.clear();
        selectedFacture = null;
    }
}
