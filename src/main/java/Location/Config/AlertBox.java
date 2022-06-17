package Location.Config;

import Location.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import java.io.IOException;

public class AlertBox {
    static public Stage stage;
    public static void display(String view) throws IOException {
        stage=new Stage();
        Parent root= FXMLLoader.load(App.class.getResource(view+".fxml"));
        Scene scene=new Scene(root);
        stage.setMinHeight(650);
        stage.setMinWidth(900);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/images/icone.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        JMetro jMetro = new JMetro(App.jMetro.getStyle());
        jMetro.setScene(scene);
        stage.showAndWait();
    }
}
