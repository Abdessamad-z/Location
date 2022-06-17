package Location.Controllers;

import Location.Classes.Vehicule;
import Location.Models.VehiculeModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class VehiculeController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnImage;

    @FXML
    private Button btnClear;

    @FXML
    private ImageView image;

    @FXML
    private ChoiceBox<String> carbu;

    @FXML
    private DatePicker circu;

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
    private TableColumn<Vehicule, Boolean> colDispo;

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

    @FXML
    private CheckBox dispo;

    @FXML
    private TextField km;

    @FXML
    private TextField marque;

    @FXML
    private TextField matricule;

    @FXML
    private TextField modele;

    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> type;

    private InputStream selectedImage;

    @FXML
    private Label errorlog;

    private VehiculeModel vehiculeModel = new VehiculeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carbu.getItems().addAll("Essence", "Gazole", "Electrique", "hybrid");
        type.getItems().addAll("Berline", "Coupé", "Familiale", "Pickup", "4×4", "SUV");
        try {
            vehiculeModel.updateDispo();
            showVehicule(vehiculeModel.allVehiculeList());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted=!(matricule.getText().isEmpty()||marque.getText().isEmpty()||modele.getText().isEmpty()||circu.getValue()==null||type.getValue().isEmpty()||carbu.getValue().isEmpty()||km.getText().isEmpty());
        if(event.getSource() == btnInsert) {
            if(areAllInserted) {
                insertVehicule();
                errorlog.setText("");
            }else{
                errorlog.setText("Tout les champs sont obligatoire!");
            }

        }
        if(event.getSource() == btnUpdate) {
            if(areAllInserted) {
                updateVehicule();
                errorlog.setText("");
            }
            else {
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnDelete) {
            deleteVehicule();
        }
        if (event.getSource() == btnImage) {
            addImage();
        }
        if (event.getSource() == btnClear) {
            clearImage();
        }
    }

    public void showVehicule(ObservableList<Vehicule> vehiculeList) throws SQLException {
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCarbu.setCellValueFactory(new PropertyValueFactory<>("carburant"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));
        colCircu.setCellValueFactory(new PropertyValueFactory<>("circulation"));
        colDispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        colCodePark.setCellValueFactory(new PropertyValueFactory<>("parkId"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        vehiculeTable.setItems(vehiculeList);
    }


    private void insertVehicule() throws SQLException, IOException {
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.mark(Integer.MAX_VALUE);
        Vehicule vehicule = new Vehicule(matricule.getText(), marque.getText(), modele.getText(), type.getValue(), carbu.getValue(),
                Double.valueOf(km.getText()), Date.valueOf(circu.getValue()), dispo.isSelected(), selectedImage, null);
        try {
            vehiculeModel.add(vehicule);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            selectedImage.reset();
        }
        showVehicule(vehiculeModel.allVehiculeList());
        back(true);
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.reset();
        clear();
    }

    private void updateVehicule() throws SQLException, IOException {
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.mark(Integer.MAX_VALUE);
        Vehicule vehicule = new Vehicule(matricule.getText(), marque.getText(), modele.getText(), type.getValue(), carbu.getValue(),
                Double.valueOf(km.getText()), Date.valueOf(circu.getValue()), dispo.isSelected(), selectedImage, null);
        vehiculeModel.update(vehicule);
        showVehicule(vehiculeModel.allVehiculeList());
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.reset();
    }

    private void deleteVehicule() throws SQLException {
        Vehicule vehicule = new Vehicule(matricule.getText(), null, null, null, null, null, null, null, null, null);
        vehiculeModel.delete(vehicule);
        showVehicule(vehiculeModel.allVehiculeList());
        back(true);
        clear();
        btnInsert.requestFocus();
    }

    private void addImage() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisie une image");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.jpe", "*.jfif", "*.png"));
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            image.setImage(new Image(new FileInputStream(selectedFile)));
            selectedImage = new FileInputStream(selectedFile);
        }
    }

    private void clearImage() {
        image.setImage(null);
        selectedImage=null;
    }

    public void onTableClick() throws IOException, SQLException {
        errorlog.setText("");
        ObservableList<Vehicule> vehiculelist = vehiculeTable.getSelectionModel().getSelectedItems();
        if (vehiculelist.isEmpty())
            return;
        back(false);
        Vehicule vehicule = vehiculelist.get(0);
        matricule.setText(vehicule.getId());
        marque.setText(vehicule.getMarque());
        modele.setText(vehicule.getModele());
        type.setValue(vehicule.getType());
        carbu.setValue(vehicule.getCarburant());
        km.setText(String.valueOf(vehicule.getKm()));
        circu.setValue(vehicule.getCirculation().toLocalDate());
        dispo.setSelected(vehicule.isDispo());
        selectedImage = vehicule.getImage();
        if (selectedImage != null) {
            selectedImage.mark(Integer.MAX_VALUE);
            image.setImage(new Image(selectedImage));
            selectedImage.reset();
        } else
            image.setImage(null);
    }

    @FXML
    void searchVehicule() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showVehicule(vehiculeModel.allVehiculeList());
        else {
            showVehicule(vehiculeModel.searchVehicule(key));
        }
    }

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
    }

    private void clear() {
        matricule.clear();
        marque.clear();
        modele.clear();
        type.setValue(null);
        carbu.setValue(null);
        km.clear();
        circu.setValue(null);
        dispo.setSelected(false);
        image.setImage(null);
        selectedImage = null;
    }
}
