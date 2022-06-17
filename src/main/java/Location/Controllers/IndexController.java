package Location.Controllers;

import Location.App;
import Location.Config.SwitchingPanes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Location.App.jMetro;
import static Location.App.stg;

public class IndexController implements Initializable {

    @FXML
    private Button admin;

    @FXML
    private BorderPane mainPane;
    @FXML
    private TextFlow flow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LoginController.utilisateur.getAdmin().equals(true))
            admin.setDisable(false);
        Text text1=new Text("Application desktop java de location de vehicule realisé a l'aide de JavaFx pour les interfaces graphiques, et mysql pour le stockage de données. Java jdbc a eté utilisé pour la transmition des requetes.\n\n");
        Text text2=new Text("Le projet est realisée par :\n");
        text2.setStyle("-fx-underline: true; -fx-text-fill: #0085cd");
        Text text3=new Text("-- Abdessamad zouiten -- Yassine Tiatro -- Anass Adiouane ---\n\n");
        text3.setStyle("-fx-font-weight: bold");
        Text text4=new Text("Encadré par :\n");
        text4.setStyle("-fx-underline: true; -fx-text-fill: #0085cd");
        Text text5=new Text("-- Mr. Walid abdellaziz --");
        text5.setStyle("-fx-font-weight: bold");
        flow.getChildren().addAll(text1,text2,text3,text4,text5);
    }

    public void homeClick() {
        try {
            App l = new App();
            l.changeScene("Index.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            App l = new App();
            stg.setMaximized(false);
            stg.setMinHeight(0);
            stg.setMinWidth(0);
            stg.setWidth(App.loginWidth);
            stg.setHeight(App.loginHeight);
            stg.setResizable(false);
            l.changeScene("Login.fxml");
            stg.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClientClick() {
        switchPase("Client");
    }

    @FXML
    void onParkingClick() {
        switchPase("Parking");
    }

    @FXML
    void onVehiculeClick() {
        switchPase("Vehicule");
    }

    @FXML
    void onReservationClick() {
        switchPase("Reservation");
    }

    @FXML
    void onContratClick() {
        switchPase("Contrat");
    }

    @FXML
    void onFactureClick() {
        switchPase("Facture");
    }

    @FXML
    void onSanctionClick() {
        switchPase("Sanction");
    }

    @FXML
    void onAdminClick() {
        switchPase("Utilisateur");
    }


    private void switchPase(String pane) {
        Pane view = SwitchingPanes.getPage(pane);
        mainPane.setCenter(view);
    }
    public void darkMode(){
        if(jMetro.getStyle()== Style.DARK)
            jMetro.setStyle(Style.LIGHT);
        else
            jMetro.setStyle(Style.DARK);
    }
}
