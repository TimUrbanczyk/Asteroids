module com.example.asteroids {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.asteroids to javafx.fxml;
    exports com.example.asteroids;
    exports com.example.asteroids.Asteroids;
    opens com.example.asteroids.Asteroids to javafx.fxml;
    exports com.example.asteroids.GUI;
    opens com.example.asteroids.GUI to javafx.fxml;
    exports com.example.asteroids.Weapons;
    opens com.example.asteroids.Weapons to javafx.fxml;
}