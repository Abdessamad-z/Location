package Location;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class App extends Application {

    public static Stage stg;
    public static double loginWidth;
    public static double loginHeight;
    public static JMetro jMetro;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stg=stage;
        stg.setResizable(false);
        Parent root= FXMLLoader.load(App.class.getResource("login.fxml"));
        Scene scene=new Scene(root, Color.BLACK);
        jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stg.getIcons().add(new Image("file:src/main/resources/images/icone.png"));
        stg.setScene(scene);
        stg.setTitle("Location");
        stg.show();
        loginWidth=stg.getWidth();
        loginHeight=stg.getHeight();
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane= FXMLLoader.load(App.class.getResource(fxml));
        stg.getScene().setRoot(pane);
        //stg.centerOnScreen();
    }

}