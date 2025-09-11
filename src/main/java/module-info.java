module com.example.asteroids {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires jdk.jsobject;
    requires com.fasterxml.jackson.databind;


    opens com.example.asteroids to javafx.fxml;
    exports com.example.asteroids;
    exports com.example.asteroids.Asteroids;
    opens com.example.asteroids.Asteroids to javafx.fxml;
    exports com.example.asteroids.Level;
    opens com.example.asteroids.Level to javafx.fxml;
    exports com.example.asteroids.Weapons;
    opens com.example.asteroids.Weapons to javafx.fxml;
    exports com.example.asteroids.Player;
    opens com.example.asteroids.Player to javafx.fxml;
    exports com.example.asteroids.Descriptions;
    opens com.example.asteroids.Descriptions to javafx.fxml;
    exports com.example.asteroids.Journal;
    opens com.example.asteroids.Journal to javafx.fxml;
}