package Location.Config;

import Location.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class SwitchingPanes {

    public SwitchingPanes(){
        super();
    }
    public static Pane getPage(String fileName){
        Pane pane = null;
        try{
            URL fileUrl= App.class.getResource("/Location/"+fileName+".fxml");
            pane= FXMLLoader.load(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
