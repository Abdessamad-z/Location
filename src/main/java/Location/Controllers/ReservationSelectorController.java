package Location.Controllers;

import Location.Config.AlertBox;
import Location.Classes.Reservation;
import Location.Models.ReservationModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservationSelectorController implements Initializable {

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> colClient;

    @FXML
    private TableColumn<Reservation, Date> colDepart;

    @FXML
    private TableColumn<Reservation, Integer> colId;

    @FXML
    private TableColumn<Reservation, Date> colReseration;

    @FXML
    private TableColumn<Reservation, Date> colRetour;

    @FXML
    private TableColumn<Reservation, String> colVehicule;

    @FXML
    private TextField search;

    private Integer selectedReservationId;

    static public Integer reservationId;

    private ReservationModel reservationModel = new ReservationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showReservation(reservationModel.allReservationList("Non validée"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        reservationId = selectedReservationId;
        AlertBox.stage.close();
    }

    public void showReservation(ObservableList<Reservation> vehiculeList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colReseration.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDepart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        colRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
        colVehicule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        reservationTable.setItems(vehiculeList);
    }

    @FXML
    void onTableClick() {
        ObservableList<Reservation> reservationList = reservationTable.getSelectionModel().getSelectedItems();
        if (reservationList.isEmpty())
            return;
        selectedReservationId = reservationList.get(0).getCode();
    }

    @FXML
    void searchReservation() throws SQLException {
        String key = search.getText();
        if (key.isEmpty())
            showReservation(reservationModel.allReservationList("all"));
        else {
            showReservation(reservationModel.searchReservation(key));
        }
    }

}
