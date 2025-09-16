package com.example.asteroids;

import com.example.asteroids.Level.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        MainWindowController.setHostServices(getHostServices());
        stage.setTitle("Orbitbreaker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setAlwaysOnTop(true);
    }

    public static void main(String[] args) {
        launch();
    }
}