package Location.Controllers;

import Location.Classes.Vehicule;
import Location.Config.AlertBox;
import Location.Models.VehiculeModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VehiculeSelectorController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Vehicule> vehiculeTable;

    @FXML
    private TableColumn<Vehicule, String> colMatricule;

    @FXML
    private TableColumn<Vehicule, String> colCarbu;

    @FXML
    private TableColumn<Vehicule, Date> colCircu;

    @FXML
    private TableColumn<Vehicule, Integer> colCodePark;

    @FXML
    private TableColumn<Vehicule, Double> colKm;

    @FXML
    private TableColumn<Vehicule, String> colMarque;

    @FXML
    private TableColumn<Vehicule, String> colType;

    @FXML
    private TableColumn<Vehicule, String> colModele;

    @FXML
    private TableColumn<Vehicule, FileInputStream> colImage;

    private InputStream selectedImage;

    @FXML
    private TextField search;

    private String selectedVehiculeId;

    static public String vehiculeId;

    private VehiculeModel vehiculeModel = new VehiculeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehiculeId = null;
        try {
            showVehicule(vehiculeModel.dispoVehiculeList());
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
        vehiculeId = selectedVehiculeId;
        AlertBox.stage.close();
    }

    public void showVehicule(ObservableList<Vehicule> vehiculeList) throws SQLException {
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCarbu.setCellValueFactory(new PropertyValueFactory<>("carburant"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));
        colCircu.setCellValueFactory(new PropertyValueFactory<>("circulation"));
        colCodePark.setCellValueFactory(new PropertyValueFactory<>("parkId"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        vehiculeTable.setItems(vehiculeList);
    }


    public void onTableClick() throws IOException {
        ObservableList<Vehicule> vehiculelist = vehiculeTable.getSelectionModel().getSelectedItems();
        if (vehiculelist.isEmpty())
            return;
        selectedVehiculeId = vehiculelist.get(0).getId();
        selectedImage = vehiculelist.get(0).getImage();
        if (selectedImage != null) {
            selectedImage.mark(Integer.MAX_VALUE);
            image.setImage(new Image(selectedImage));
            selectedImage.reset();
        } else
            image.setImage(null);
    }

    @FXML
    void searchVehicule() throws SQLException {
        String key = search.getText();
        if (key.isEmpty())
            showVehicule(vehiculeModel.allVehiculeList());
        else {
            showVehicule(vehiculeModel.searchVehicule(key));
        }
    }


}
