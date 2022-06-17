package Location.Controllers;

import Location.App;
import Location.Classes.Utilisateur;
import Location.Models.UtilisateurModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static Location.App.stg;


public class LoginController {
    @FXML
    private Label errorlog;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public static Utilisateur utilisateur;

    private UtilisateurModel utilisateurModel = new UtilisateurModel();


    @FXML
    protected void onLoginClick() throws SQLException {
        App l = new App();
        String identifiant = username.getText();
        String mdp = password.getText();
        utilisateur = utilisateurModel.check(identifiant, mdp);
        if (utilisateur == null)
            errorlog.setText("Nom d'utilisateur ou mot de passe incorrecte, veuillez ressayer");
        else {
            try {
                new UtilisateurModel().currentUser=identifiant;
                l.changeScene("index.fxml");
                stg.setResizable(true);
                stg.setMaximized(true);
                stg.setMinWidth(1280);
                stg.setMinHeight(720);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
