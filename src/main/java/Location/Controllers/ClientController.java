package Location.Controllers;

import Location.Classes.Client;
import Location.Models.ClientModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TextArea adress;

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
    private TableColumn<Client, InputStream> colPermis;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private Label errorlog;

    @FXML
    private TextField search;

    private Client selectedClient;

    private ClientModel clientModel = new ClientModel();

    private InputStream selectedImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showClients(clientModel.allClientList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted=!(name.getText().isEmpty()||phone.getText().isEmpty()||adress.getText().isEmpty());
        if(event.getSource() == btnInsert) {
            if(areAllInserted){
                insertClient();
                errorlog.setText("");
            }
            else {
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if(event.getSource() == btnUpdate) {
            if(areAllInserted) {
                updateClient();
                errorlog.setText("");
            }
            else{
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnDelete) {
            deleteClient();
        }
        if (event.getSource() == btnImage) {
            addImage();
        }
        if (event.getSource() == btnClear) {
            clearImage();
        }
    }

    public void showClients(ObservableList<Client> clientList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("gsm"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        colPermis.setCellValueFactory(new PropertyValueFactory<>("permis"));
        clientTable.setItems(clientList);
    }

    private void insertClient() throws SQLException, IOException {
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.mark(Integer.MAX_VALUE);
        Client client = new Client(null, name.getText(), phone.getText(), adress.getText(), selectedImage);
        try {
            clientModel.addClient(client);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            selectedImage.reset();
        }
        showClients(clientModel.allClientList());
        back(true);
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.reset();
        clear();
    }

    private void updateClient() throws SQLException, IOException {
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.mark(Integer.MAX_VALUE);
        Client client = new Client(selectedClient.getId(), name.getText(), phone.getText(), adress.getText(), selectedImage);
        clientModel.updateClient(client);
        showClients(clientModel.allClientList());
        if (!(selectedImage instanceof FileInputStream) && selectedImage != null)
            selectedImage.reset();
    }

    private void deleteClient() throws SQLException {
        clientModel.deleteClient(selectedClient);
        showClients(clientModel.allClientList());
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

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
    }

    public void onTableClick() throws IOException {
        errorlog.setText("");
        ObservableList<Client> clientlist = clientTable.getSelectionModel().getSelectedItems();
        if (clientlist.isEmpty())
            return;
        selectedClient = clientlist.get(0);
        name.setText(selectedClient.getName());
        phone.setText(selectedClient.getGsm().substring(1));
        adress.setText(selectedClient.getAdress());
        selectedImage = selectedClient.getPermis();
        if (selectedImage != null) {
            selectedImage.mark(Integer.MAX_VALUE);
            image.setImage(new Image(selectedImage));
            selectedImage.reset();
        } else
            image.setImage(null);
        back(false);
        
    }

    @FXML
    void searchClient() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showClients(clientModel.allClientList());
        else {
            ObservableList<Client> clientlist = clientModel.searchClient(key);
            showClients(clientlist);
        }
    }

    private void clear() {
        selectedClient = null;
        name.clear();
        phone.clear();
        adress.clear();
        image.setImage(null);
        selectedImage = null;
    }

}
