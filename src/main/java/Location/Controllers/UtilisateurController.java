package Location.Controllers;

import Location.Classes.Utilisateur;
import Location.Models.UtilisateurModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UtilisateurController implements Initializable {

    @FXML
    private CheckBox admin;

    @FXML
    private TextArea adress;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;


    @FXML
    private Label errorlog;

    @FXML
    private TableView<Utilisateur> utilisateurTable;

    @FXML
    private TableColumn<Utilisateur, String> colAdress;

    @FXML
    private TableColumn<Utilisateur, String> colId;

    @FXML
    private TableColumn<Utilisateur, String> colName;

    @FXML
    private TableColumn<Utilisateur, String> colPhone;

    @FXML
    private TableColumn<Utilisateur, String> colPwd;

    @FXML
    private TableColumn<Utilisateur, Boolean> colAdmin;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField pwd;

    @FXML
    private TextField search;

    private Utilisateur selectedUtilisateur;

    private UtilisateurModel utilisateurModel = new UtilisateurModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showUtilisateur(utilisateurModel.allUtilisateurList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showUtilisateur(ObservableList<Utilisateur> utilisateursList) throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("num"));
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        utilisateurTable.setItems(utilisateursList);
    }

    @FXML
    void btnAction(ActionEvent event) throws SQLException, IOException {
        boolean areAllInserted=!(id.getText().isEmpty()||pwd.getText().isEmpty()||name.getText().isEmpty()||adress.getText().isEmpty()||phone.getText().isEmpty());
        if(event.getSource() == btnInsert) {
            if(areAllInserted) {
                if(!utilisateurModel.checkId(id.getText())){
                    insert();
                    errorlog.setText("");
                }
                else {
                    errorlog.setText("Utilisateur de même identifiant existe déja!");
                }
            }else{
                errorlog.setText("Tout les champ sont obligatoire!");
            }
        }
        if (event.getSource() == btnDelete) {
            delete();
        }
    }

    private void insert() throws SQLException, IOException {
        Utilisateur utilisateur = new Utilisateur(id.getText(), pwd.getText(), name.getText(), adress.getText(), phone.getText(), admin.isSelected());
        utilisateurModel.add(utilisateur);
        showUtilisateur(utilisateurModel.allUtilisateurList());
        clear();
    }

    private void update() throws SQLException, IOException {
        Utilisateur utilisateur = new Utilisateur(selectedUtilisateur.getId(), pwd.getText(), name.getText(), adress.getText(), phone.getText(), admin.isSelected());
        utilisateurModel.update(utilisateur);
        showUtilisateur(utilisateurModel.allUtilisateurList());
        back(true);
    }

    private void delete() throws SQLException, IOException {
        Utilisateur utilisateur = new Utilisateur(selectedUtilisateur.getId(), null, null, null, null, null);
        try {
            if(utilisateurModel.currentUser.equals(selectedUtilisateur.getId()))
                throw new Exception();
            utilisateurModel.delete(utilisateur);
            showUtilisateur(utilisateurModel.allUtilisateurList());
            back(true);
            clear();
            btnInsert.requestFocus();
        }catch(Exception e){
            errorlog.setText("Vous ne pouvez pas supprimer vous même!");
        }
    }


    @FXML
    void onTableClick() {
        errorlog.setText("");
        ObservableList<Utilisateur> utilisateurList = utilisateurTable.getSelectionModel().getSelectedItems();
        if (utilisateurList.isEmpty())
            return;
        selectedUtilisateur = utilisateurList.get(0);
        id.setText(selectedUtilisateur.getId());
        pwd.setText(selectedUtilisateur.getPwd());
        name.setText(selectedUtilisateur.getName());
        adress.setText(selectedUtilisateur.getAdress());
        phone.setText(selectedUtilisateur.getNum().substring(1));
        admin.setSelected(selectedUtilisateur.getAdmin());
        back(false);
    }

    @FXML
    void searchSanction() throws SQLException {
        errorlog.setText("");
        String key = search.getText();
        if (key.isEmpty())
            showUtilisateur(utilisateurModel.allUtilisateurList());
        else {
            showUtilisateur(utilisateurModel.searchUtilisateur(key));
        }
    }

    private void back(Boolean b) {
        btnDelete.setDisable(b.equals(true));
        btnUpdate.setDisable(b.equals(true));
    }

    private void clear() {
        id.clear();
        pwd.clear();
        name.clear();
        adress.clear();
        phone.clear();
        admin.setSelected(false);
        selectedUtilisateur = null;
    }
}
