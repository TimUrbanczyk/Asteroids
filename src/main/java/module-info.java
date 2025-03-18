module com.example.asteroids {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.asteroids to javafx.fxml;
    exports com.example.asteroids;
}