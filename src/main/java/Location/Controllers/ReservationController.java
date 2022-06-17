package Location.Controllers;

import Location.Classes.Reservation;
import Location.Config.AlertBox;
import Location.Models.ReservationModel;
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
import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private Button btnClient;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnVehicule;

    @FXML
    private TextField client;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> colClient;

    @FXML
    private TableColumn<Reservation, Date> colDepart;

    @FXML
    private TableColumn<Reservation, String> colEtat;

    @FXML
    private TableColumn<Reservation, Integer> colId;

    @FXML
    private TableColumn<Reservation, Date> colReseration;

    @FXML
    private TableColumn<Reservation, Date> colRetour;

    @FXML
    private TableColumn<Reservation, String> colVehicule;

    @FXML
    private DatePicker dateDepart;

    @FXML
    private DatePicker dateRetour;

    @FXML
    private ChoiceBox<String> etat;

    @FXML
    private TextField search;

    @FXML
    private Label errorlog;

    @FXML
    private TextField vehicule;

    private Reservation selectedReservation;

    private ReservationModel reservationModel = new ReservationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etat.getItems().addAll("Non validée", "Validée", "Annulée");
        try {
            reservationModel.updateAll();
            showReservation(reservationModel.allReservationList("all"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted = !(dateDepart.getValue() == null || dateRetour.getValue() == null || client.getText()==null ||
                vehicule.getText()==null);
        if (event.getSource() == btnInsert) {
            if (areAllInserted) {
                if(dateCheck()) {
                    insertReservation();
                    errorlog.setText("");
                }
                else
                    errorlog.setText("Il existe une erreur au niveau des dates");
            } else {
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnUpdate) {
            if (areAllInserted) {
                if(dateCheck()) {
                    insertReservation();
                    errorlog.setText("");
                }
                else
                    errorlog.setText("Il existe une erreur au niveau des dates");
            } else {
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }

        if (event.getSource() == btnDelete)

            deleteReservation();

        if (event.getSource() == btnVehicule)
            selectVehicule();

        if (event.getSource() == btnClient)
            selectClient();
        if (event.getSource() == btnBack) {
            back(true);
            clear();
        }
    }

    private boolean dateCheck(){
        Duration duration = Duration.between(LocalDate.now().atStartOfDay(),
                dateDepart.getValue().atStartOfDay());
        double diff=duration.toDays();
        duration= Duration.between(dateDepart.getValue().atStartOfDay(),
                dateRetour.getValue().atStartOfDay());
        double diff2=duration.toDays();
        return diff>0 && diff2>0;
    }
    private void insertReservation() throws SQLException {
        Reservation reservation = new Reservation(null, null, Date.valueOf(dateDepart.getValue()),
                Date.valueOf(dateRetour.getValue()), "Non validée", Integer.valueOf(client.getText()), vehicule.getText());
        reservationModel.add(reservation);
        showReservation(reservationModel.allReservationList("all"));
        back(true);
        clear();
    }

    private void updateReservation() throws SQLException {
        Reservation reservation = new Reservation(selectedReservation.getCode(), null, Date.valueOf(dateDepart.getValue()),
                Date.valueOf(dateRetour.getValue()), etat.getValue(), Integer.valueOf(client.getText()), vehicule.getText());
        reservationModel.update(reservation);
        showReservation(reservationModel.allReservationList("all"));
    }

    private void deleteReservation() throws SQLException {
        Reservation reservation = new Reservation(selectedReservation.getCode(), null, null, null, null, null, selectedReservation.getMatricule());
        reservationModel.delete(reservation);
        showReservation(reservationModel.allReservationList("all"));
        back(true);
        clear();
        btnInsert.requestFocus();
    }

    public void selectVehicule() throws IOException {
        AlertBox.display("VehiculeSelector");
        if (VehiculeSelectorController.vehiculeId != null) {
            vehicule.setText(VehiculeSelectorController.vehiculeId);
        }
    }

    public void selectClient() throws IOException {
        AlertBox.display("ClientSelector");
        if (ClientSelectorController.clientId != null) {
            client.setText(ClientSelectorController.clientId.toString());
        }
    }

    public void showReservation(ObservableList<Reservation> vehiculeList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colReseration.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDepart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        colRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
        colVehicule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        reservationTable.setItems(vehiculeList);
    }


    @FXML
    void onTableClick() {
        errorlog.setText("");
        ObservableList<Reservation> reservationList = reservationTable.getSelectionModel().getSelectedItems();
        if (reservationList.isEmpty())
            return;
        selectedReservation = reservationList.get(0);
        dateDepart.setValue(selectedReservation.getDateDepart().toLocalDate());
        dateRetour.setValue(selectedReservation.getDateRetour().toLocalDate());
        etat.setValue(selectedReservation.getEtat());
        client.setText(selectedReservation.getCodeClient().toString());
        vehicule.setText(selectedReservation.getMatricule());
        back(false);
    }

    @FXML
    void searchReservation() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showReservation(reservationModel.allReservationList("all"));
        else {
            showReservation(reservationModel.searchReservation(key));
        }
    }

    private void back(Boolean b) {
        btnInsert.setDisable(b.equals(false));
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
        btnBack.setDisable(b.equals(true));
        if (b)
            etat.setValue(null);
        etat.setDisable(b.equals(true));
        btnInsert.requestFocus();
    }

    private void clear() {
        dateDepart.setValue(null);
        dateRetour.setValue(null);
        etat.setValue(null);
        vehicule.clear();
        client.clear();
        selectedReservation = null;
    }
}
