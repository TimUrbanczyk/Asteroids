package com.example.asteroids.GUI;

import com.example.asteroids.Items.LaserItem;
import com.example.asteroids.Asteroids.BigAsteroid;
import com.example.asteroids.Asteroids.HealingAsteroid;
import com.example.asteroids.Asteroids.SmallAsteroid;
import com.example.asteroids.PlayerPackage.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Button closeJournalWindowButton;
    @FXML
    private AnchorPane journalAnchorPane;
    @FXML
    private Separator separator1;
    @FXML
    private Label journalLabel;
    @FXML
    public ListView listView;



    @FXML
    private void onButtonCloseJournalWindow(ActionEvent event) {
        Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();


        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //move the elements to the right position according to screensize etc
        closeJournalWindowButton.setLayoutX(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/7));
        separator1.setPrefWidth(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/5));
        journalLabel.setLayoutX((journalAnchorPane.getPrefWidth()+journalLabel.getWidth())/2);
        listView.setPrefHeight(journalAnchorPane.getPrefHeight()-70);

        //add the journalpages to the listview
        ObservableList journalPages = FXCollections.observableArrayList();
        journalPages.addAll(Player.getName(), SmallAsteroid.getName(), BigAsteroid.getName(), HealingAsteroid.getName(),
                            LaserItem.getName());
        listView.setItems(journalPages);

    }
}
