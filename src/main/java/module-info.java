module Location {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    requires java.sql;
    requires org.jfxtras.styles.jmetro;



    opens Location to javafx.fxml;
    exports Location;
    exports Location.Config;
    exports Location.Classes;
    exports Location.Controllers;
    opens Location.Config to javafx.fxml;
    opens Location.Classes to javafx.fxml;
    opens Location.Controllers to javafx.fxml;
}