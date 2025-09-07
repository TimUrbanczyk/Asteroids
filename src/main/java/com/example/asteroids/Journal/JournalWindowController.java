package com.example.asteroids.Journal;

import com.example.asteroids.Asteroids.*;
import com.example.asteroids.Descriptions.*;
import com.example.asteroids.Items.BoostItem;
import com.example.asteroids.Items.LaserItem;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
    private Text journalText;



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
                journalText.setText(DescriptionSmallAsteroid.getDescription());
                journalImageview.setImage(new SmallAsteroid().getAsteroidImage().getImage());
                break;
            case "ShootingStar":
                journalText.setText(DescriptionHealingAsteroid.getDescription());
                journalImageview.setImage(new HealingAsteroid().getAsteroidImage().getImage());
                break;
            case "BigAsteroid":
                journalText.setText(DescriptionBigAsteroid.getDescription());
                journalImageview.setImage(new BigAsteroid().getAsteroidImage().getImage());
                break;
            case "Spaceship":
                journalText.setText(DescriptionPlayer.getDescription());
                journalImageview.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/SpaceshipPlayer.png")).toExternalForm()));
                break;
            case "LaserUpgread":
                journalText.setText(DescriptionLaser.getDescription());
                journalImageview.setImage(new LaserItem().getAsteroidImage().getImage());
                break;
            case "BoostUpgread":
                journalText.setText(DescriptionBoost.getDescription());
                journalImageview.setImage(new BoostItem().getAsteroidImage().getImage());
                break;
            case "DualityCores":
                journalText.setText(DescriptionDualityCores.getDescription());
                journalImageview.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/DualityMainCore.png")).toExternalForm()));
                break;
            case "Disruptor":
                journalText.setText(DescriptionDisruptor.getDescription());
                journalImageview.setImage(new Disruptor().getAsteroidImage().getImage());
                break;
            default:
                break;
        }


    }

    private DiscoveredEntities getDiscoveredEntities(){
        ObjectMapper mapper = new ObjectMapper();
        File discoveredEntitiesFile = new File("src/main/java/com/example/asteroids/Journal/DiscoveredEntities.json");
        try {
            return mapper.readValue(discoveredEntitiesFile, DiscoveredEntities.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        closeJournalWindowButton.setLayoutX(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/7));

        separator1.setPrefWidth(journalAnchorPane.getPrefWidth()+(journalAnchorPane.getPrefWidth()/5));
        separator2.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);
        separator3.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);

        journalLabel.setLayoutX((journalAnchorPane.getPrefWidth()+journalLabel.getWidth())/2);

        listView.setPrefHeight(journalAnchorPane.getPrefHeight()-70);

        ObservableList<Object> journalPages = FXCollections.observableArrayList();
        List<String> discoveredEntities  = getDiscoveredEntities().getDiscovered();
        journalPages.addAll(discoveredEntities);

        listView.setItems(journalPages);

        journalImageview.setScaleX(0.5);
        journalImageview.setScaleY(0.5);
        journalImageview.setLayoutX(journalAnchorPane.getPrefWidth()-journalImageview.getFitWidth()/4);

        journalText.setFill(Color.WHITE);
        journalText.setText("");



    }
}
