package Location.Controllers;

import Location.Classes.Contrat;
import Location.Config.AlertBox;
import Location.Models.ContratModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ContratController implements Initializable {

    @FXML
    private TextField reservation;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private Label restitutionLabel;

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
    private TableColumn<Contrat, Boolean> colSigne;

    @FXML
    private TableColumn<Contrat, Integer> colReseration;

    @FXML
    private DatePicker dateEcheance;

    @FXML
    private DatePicker dateRestitution;

    @FXML
    private TextField search;

    @FXML
    private Label errorlog;

    private ContratModel contratModel = new ContratModel();

    private Contrat selectedContrat;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showContrats(contratModel.allContratList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showContrats(ObservableList<Contrat> contratList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateContrat.setCellValueFactory(new PropertyValueFactory<>("dateContrat"));
        colDateEcheance.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        colDateRestitution.setCellValueFactory(new PropertyValueFactory<>("dateRestitution"));
        colSigne.setCellValueFactory(new PropertyValueFactory<>("signe"));
        colReseration.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        contratTable.setItems(contratList);
    }


    @FXML
    void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted=!(dateEcheance.getValue()==null||reservation.getText().isEmpty());
        if(event.getSource() == btnInsert) {
            if(areAllInserted) {
                if(dateCheck()) {
                    insertContrat();
                    errorlog.setText("");
                }
                else
                        errorlog.setText("La date d'echéance est antérieure a la date actuelle");
            }else{
                errorlog.setText("Tout les champs sont obligatoire!");
            }

        }
        if(event.getSource() == btnUpdate) {
            if(areAllInserted) {
                if(dateCheck()) {
                    insertContrat();
                    errorlog.setText("");
                }
                else
                    errorlog.setText("La date d'echéance est antérieure a la date actuelle");
            }
            else {
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnDelete) {
            deleteContrat();
        }
        if (event.getSource() == btnBack) {
            back();
            clear();
        }
        if (event.getSource() == btnReservation) {
            selectReservation();
        }
    }

    private boolean dateCheck(){
        Duration duration = Duration.between(LocalDate.now().atStartOfDay(),
                dateEcheance.getValue().atStartOfDay());
        double diff=duration.toDays();
        return diff>0;
    }

    private void insertContrat() throws SQLException, IOException {
        Contrat contrat = new Contrat(null, null, Date.valueOf(dateEcheance.getValue()), null, null, Integer.valueOf(reservation.getText()));
        contratModel.add(contrat);
        showContrats(contratModel.allContratList());
        clear();
    }

    private void updateContrat() throws SQLException, IOException {
        Contrat contrat = new Contrat(selectedContrat.getId(), null, Date.valueOf(dateEcheance.getValue()), Date.valueOf(dateRestitution.getValue()), null, Integer.valueOf(reservation.getText()));
        contratModel.update(contrat);
        showContrats(contratModel.allContratList());
    }

    private void deleteContrat() throws SQLException, IOException {
        Contrat contrat = new Contrat(selectedContrat.getId(), null, null, null, null, selectedContrat.getIdReservation());
        contratModel.delete(contrat);
        showContrats(contratModel.allContratList());
        back();
        btnInsert.requestFocus();
        clear();
    }

    private void back() {
        selectedContrat = null;
        dateRestitution.setValue(null);
        btnBack.setDisable(true);
        dateRestitution.setDisable(true);
        restitutionLabel.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnInsert.setDisable(false);
        btnInsert.requestFocus();
    }

    private void selectReservation() throws IOException {
        AlertBox.display("ReservationSelector");
        if (ReservationSelectorController.reservationId != null) {
            reservation.setText(String.valueOf(ReservationSelectorController.reservationId));
        }
    }

    @FXML
    void onTableClick() {
        errorlog.setText("");
        ObservableList<Contrat> contratList = contratTable.getSelectionModel().getSelectedItems();
        if (contratList.isEmpty())
            return;
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        restitutionLabel.setDisable(false);
        dateRestitution.setDisable(false);
        btnBack.setDisable(false);
        btnInsert.setDisable(true);
        selectedContrat = contratList.get(0);
        dateEcheance.setValue(selectedContrat.getDateEcheance().toLocalDate());
        reservation.setText(selectedContrat.getIdReservation().toString());
        LocalDate fill = selectedContrat.getDateRestitution() != null ? selectedContrat.getDateRestitution().toLocalDate() : null;
        dateRestitution.setValue(fill);
    }

    @FXML
    void searchContrat() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showContrats(contratModel.allContratList());
        else {
            showContrats(contratModel.searchContrat(key));
        }
    }

    private void clear() {
        dateEcheance.setValue(null);
        dateRestitution.setValue(null);
        reservation.clear();
        selectedContrat = null;
    }

}
