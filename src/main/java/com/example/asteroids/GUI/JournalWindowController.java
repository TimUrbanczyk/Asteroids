package com.example.asteroids.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JournalWindowController implements Initializable {


    @FXML
    Button closeJournalWindowButton;
    @FXML
    AnchorPane journalAnchorPane;
    @FXML
    Separator separator1;
    @FXML
    Label journalLabel;
    @FXML
    ListView listView;

    @FXML
    private void onButtonCloseJournalWindow(ActionEvent event) {
        Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();


        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeJournalWindowButton.setLayoutX(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/7));
        separator1.setPrefWidth(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/5));
        journalLabel.setLayoutX((journalAnchorPane.getPrefWidth()+journalLabel.getWidth())/2);
        listView.setPrefHeight(journalAnchorPane.getPrefHeight()-70);

    }
}
