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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Separator separator2;
    @FXML
    private Separator separator3;
    @FXML
    private Label journalLabel;
    @FXML
    private ListView listView;
    @FXML
    private ImageView journalImageview;



    @FXML
    private void onButtonCloseJournalWindow(ActionEvent event) {
        Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();


        stage.close();
    }


    @FXML
    private void onListviewSelect(){
        String name = listView.getSelectionModel().getSelectedItem().toString();
        switch(name){
            case "SmallAsteroid":
                journalImageview.setImage(new SmallAsteroid().getAsteroidImage().getImage());
                break;
            case "ShootingStar":
                journalImageview.setImage(new HealingAsteroid().getAsteroidImage().getImage());
                break;
            case "BigAsteroid":
                journalImageview.setImage(new BigAsteroid().getAsteroidImage().getImage());
                break;
            case "Spaceship":
                journalImageview.setImage(new Image(getClass().getResource("/imgs/SpaceshipPlayer.png").toExternalForm()));
                break;
            case "LaserUpgread":
                journalImageview.setImage(new LaserItem().getAsteroidImage().getImage());
                break;
            default:
                break;
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //move the elements to the right position according to screensize etc
        closeJournalWindowButton.setLayoutX(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/7));

        separator1.setPrefWidth(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/5));
        separator2.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);
        separator3.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);

        journalLabel.setLayoutX((journalAnchorPane.getPrefWidth()+journalLabel.getWidth())/2);

        listView.setPrefHeight(journalAnchorPane.getPrefHeight()-70);

        //add the journalpages to the listview
        ObservableList journalPages = FXCollections.observableArrayList();
        journalPages.addAll(Player.getName(), SmallAsteroid.getName(), BigAsteroid.getName(), HealingAsteroid.getName(), LaserItem.getName());
        listView.setItems(journalPages);

        journalImageview.setScaleX(0.5);
        journalImageview.setScaleY(0.5);
        journalImageview.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);



    }
}
