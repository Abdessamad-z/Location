package Location.Controllers;

import Location.Classes.Parking;
import Location.Classes.Vehicule;
import Location.Models.ParkingModel;
import Location.Models.VehiculeModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ParkingController implements Initializable {
    @FXML
    private TextField arrondi;

    @FXML
    private TextField capacity;

    @FXML
    private TextField rue;

    @FXML
    private TextField search;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Parking> parckingTable;

    @FXML
    private TableColumn<Parking, Integer> colId;

    @FXML
    private TableColumn<Parking, Integer> colCap;

    @FXML
    private TableColumn<Parking, String> colRue;

    @FXML
    private TableColumn<Parking, String> colArrondi;

    @FXML
    private TableView<Vehicule> vehiculeTable;

    @FXML
    private TableColumn<Vehicule, String> matricule;

    @FXML
    private TableColumn<Vehicule, String> carbu;

    @FXML
    private TableColumn<Vehicule, Date> circu;

    @FXML
    private TableColumn<Vehicule, Integer> codePark;

    @FXML
    private TableColumn<Vehicule, Boolean> dispo;

    @FXML
    private TableColumn<Vehicule, Double> km;

    @FXML
    private TableColumn<Vehicule, String> marque;

    @FXML
    private TableColumn<Vehicule, String> type;

    @FXML
    private Button btnRevokeV;

    @FXML
    private Button btnInsertV;

    @FXML
    private Label instructions;

    @FXML
    private VBox formular;

    @FXML
    private Button retour;

    private Vehicule selectedVehicule;

    @FXML
    private Label errorlog;

    private Parking selectedParking;

    private ParkingModel parkingModel = new ParkingModel();

    private VehiculeModel vehiculeModel = new VehiculeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showParkings(parkingModel.allParkingList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showVehicule(ObservableList<Vehicule> vehiculeList) {
        matricule.setCellValueFactory(new PropertyValueFactory<>("id"));
        marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        carbu.setCellValueFactory(new PropertyValueFactory<>("carburant"));
        km.setCellValueFactory(new PropertyValueFactory<>("km"));
        circu.setCellValueFactory(new PropertyValueFactory<>("circulation"));
        dispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        codePark.setCellValueFactory(new PropertyValueFactory<>("parkId"));
        vehiculeTable.setItems(vehiculeList);
    }

    public void btnAction(ActionEvent event) throws SQLException {
        boolean areAllInserted=!(capacity.getText().isEmpty()||rue.getText().isEmpty()||arrondi.getText().isEmpty());
        if (event.getSource() == btnInsert) {
            if(areAllInserted) {
                try {
                    insertParking();
                    errorlog.setText("");
                }
                catch(NumberFormatException e){
                    errorlog.setText("La capacité doit être un entier naturel!");
                }
            }
            else{
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnUpdate) {
            if(areAllInserted) {
                try {
                    updateParking();
                    errorlog.setText("");
                }
                catch(NumberFormatException e){
                    errorlog.setText("La capacité doit être un entier naturel!");
                    errorlog.setTextFill(Color.web("#DF362D"));
                }
            }
            else{
                errorlog.setText("Tout les champ sont obligatoire!");
                errorlog.setTextFill(Color.web("#DF362D"));
            }
        }
        if (event.getSource() == btnDelete) {
            deleteParking();
        }
        if (event.getSource() == btnInsertV) {
            insertVehicule();
        }
        if (event.getSource() == btnRevokeV) {
            vehiculeModel.revoke(selectedVehicule);
            showVehicule(parkingModel.parkingVehiculeList(selectedParking));
            btnRevokeV.setDisable(true);
        }
        if (event.getSource() == retour) {
            returnInsert();
        }
    }

    public void showParkings(ObservableList<Parking> parkingList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCap.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colRue.setCellValueFactory(new PropertyValueFactory<>("rue"));
        colArrondi.setCellValueFactory(new PropertyValueFactory<>("arrondissment"));
        parckingTable.setItems(parkingList);
    }

    private void insertParking() throws SQLException {
        Parking parking = new Parking(null, Integer.valueOf(capacity.getText()), rue.getText(), arrondi.getText());
        parkingModel.add(parking);
        showParkings(parkingModel.allParkingList());
        btnRevokeV.setDisable(true);
        btnInsertV.setDisable(true);
        showVehicule(null);
        clear();
    }

    private void updateParking() throws SQLException {
        Parking parking = new Parking(selectedParking.getId(), Integer.valueOf(capacity.getText()), rue.getText(), arrondi.getText());
        parkingModel.update(parking);
        showParkings(parkingModel.allParkingList());
        back(true);
    }

    private void deleteParking() throws SQLException {
        parkingModel.delete(selectedParking);
        showParkings(parkingModel.allParkingList());
        back(true);
        clear();
        btnInsert.requestFocus();
    }

    public void onTableClick() throws SQLException {
        errorlog.setText("");
        ObservableList<Parking> parkingList = parckingTable.getSelectionModel().getSelectedItems();
        if (parkingList.isEmpty())
            return;
        btnInsertV.setDisable(false);
        selectedParking = parkingList.get(0);
        instructions.setText("La liste des vehicules dans le parking numero " + selectedParking.getId());
        capacity.setText(String.valueOf(selectedParking.getCapacity()));
        rue.setText(selectedParking.getRue());
        arrondi.setText(selectedParking.getArrondissment());
        showVehicule(parkingModel.parkingVehiculeList(selectedParking));
        btnRevokeV.setDisable(true);
        back(false);
    }

    @FXML
    void searchParking() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showParkings(parkingModel.allParkingList());
        else {
            showParkings(parkingModel.searchParking(key));
        }
    }

    public void onVehiculeTableClick() {
        ObservableList<Vehicule> vehiculelist = vehiculeTable.getSelectionModel().getSelectedItems();
        if (vehiculelist.isEmpty())
            return;
        selectedVehicule = vehiculelist.get(0);
        if (btnInsertV.getText().equals("Deposer un vehicule"))
            btnRevokeV.setDisable(false);
        else
            btnInsertV.setDisable(false);
    }

    public void insertVehicule() throws SQLException {
        if (btnInsertV.getText().equals("Deposer un vehicule")) {
            instructions.setText("La liste des vehicules libres");
            showVehicule(vehiculeModel.freeVehiculeList());
            btnInsertV.setText("Ajouter le vehicule");
            btnInsertV.setDisable(true);
            formular.setDisable(true);
            parckingTable.setDisable(true);
            btnRevokeV.setDisable(true);
            search.setDisable(true);
            retour.setDisable(false);
        } else {
            vehiculeModel.toParking(selectedVehicule, selectedParking.getId());
            btnInsertV.setText("Deposer un vehicule");
            instructions.setText("La liste des vehicules dans le parking numero " + selectedParking.getId());
            showVehicule(parkingModel.parkingVehiculeList(selectedParking));
            formular.setDisable(false);
            parckingTable.setDisable(false);
            search.setDisable(false);
            btnRevokeV.setDisable(false);
            retour.setDisable(true);
        }
    }

    public void returnInsert() throws SQLException {
        btnInsertV.setText("Deposer un vehicule");
        instructions.setText("La liste des vehicules dans le parking numero " + selectedParking.getId());
        showVehicule(parkingModel.parkingVehiculeList(selectedParking));
        formular.setDisable(false);
        parckingTable.setDisable(false);
        search.setDisable(false);
        retour.setDisable(true);
    }

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
    }

    private void clear() {
        capacity.clear();
        rue.clear();
        arrondi.clear();
        selectedParking = null;
        instructions.setText(null);
    }
}
