package com.example.asteroids;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    // Flags to track key states
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;


    private long elapsedTime = 0;
    private final long spawnInterval = 500;



    //FXML fields
    @FXML
    private Label labelAsteroids = LabelAsteroids.getInstance().getLabelAsteroids();
    @FXML
    private Button buttonStartGame;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private ImageView playerShip;

    //Create Player

    Player player = new Player(100,playerShip);


    //Fields
    private Parent[] startScreenElements = new Parent[2];




    @FXML
    private void onButtonStartGame(){

        //vanish all the gui elements from the start screen
        for(Parent element : startScreenElements){
            element.setVisible(false);
        }

        //start the game
        Main();

    }//end of onButtonStartGame

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        // Set movement and rotation flags based on key presses
        switch (event.getCode()) {
            case W:
                movingUp = true;
                break;
            case S:
                movingDown = true;
                break;
            case A:
                movingLeft = true;
                break;
            case D:
                movingRight = true;
                break;
            case LEFT:
                rotatingLeft = true;
                break;
            case RIGHT:
                rotatingRight = true;
                break;
            default:
                break;
        }
    }
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        // Reset movement and rotation flags based on key releases
        switch (event.getCode()) {
            case W:
                movingUp = false;
                break;
            case S:
                movingDown = false;
                break;
            case A:
                movingLeft = false;
                break;
            case D:
                movingRight = false;
                break;
            case LEFT:
                rotatingLeft = false;
                break;
            case RIGHT:
                rotatingRight = false;
                break;
            default:
                break;
        }
    }

    private void drawAsteroids(){
        for(Asteroid asteroid : Asteroid.getAsteroids()){


        }
    }



    //main method/loop/u know
    private void Main(){
        System.out.println(playerShip.getLayoutX());
        System.out.println(playerShip.getLayoutY());
        mainAnchorPane.setFocusTraversable(true);
        mainAnchorPane.requestFocus();

        Timeline gameloop = new Timeline(new KeyFrame(Duration.millis(7), event -> {

            // Update playerShip position based on movement flags
            drawAsteroids();
            Asteroid.moveAsteroid();
            elapsedTime+= 7;

            if(elapsedTime >= spawnInterval ){Asteroid.spawnAsteroid();mainAnchorPane.getChildren().add(Asteroid.getAsteroids().getFirst().getAsteroidImage()); elapsedTime = 0;}




            if (movingUp) {
                playerShip.setY(playerShip.getY() - player.getSpeed());
            }
            if (movingDown) {
                playerShip.setY(playerShip.getY() + player.getSpeed());
            }
            if (movingLeft) {
                playerShip.setX(playerShip.getX() - player.getSpeed());
            }
            if (movingRight) {
                playerShip.setX(playerShip.getX() + player.getSpeed());
            }


            if (rotatingLeft) {
                playerShip.setRotate(playerShip.getRotate() - player.getRotationSpeed());
            }
            if (rotatingRight) {
                playerShip.setRotate(playerShip.getRotate() + player.getRotationSpeed());
            }

            //boundary checking

            if(playerShip.getX() < -700){playerShip.setX(-700);}
            if(playerShip.getY() < -334){playerShip.setY(-334);}
            if(playerShip.getX() + playerShip.getLayoutX() > 1450){playerShip.setX(1450-playerShip.getLayoutX());}
            if(playerShip.getY() + playerShip.getLayoutY() > 750){playerShip.setY(750-playerShip.getLayoutY());}


        }));

        gameloop.setCycleCount(Timeline.INDEFINITE);
        gameloop.setAutoReverse(true);
        gameloop.play();


    }//end of Main





    private void StartAnimationAsteroidsLabel() {

        Timeline timelineAsteroidsLabel = new Timeline();
        timelineAsteroidsLabel.setAutoReverse(true);
        timelineAsteroidsLabel.setCycleCount(150);

        KeyFrame keyFrameTimelineAsteroidsLabel = new KeyFrame(Duration.millis(16.67) , actionEvent -> {

           double currentScaleX = labelAsteroids.getScaleX();
           double currentScaleY = labelAsteroids.getScaleY();

           if(currentScaleX <= 1.5 && currentScaleY <= 1.5) {labelAsteroids.setScaleX(currentScaleX + 0.01); labelAsteroids.setScaleY(currentScaleY + 0.01);}
           else{labelAsteroids.setScaleX(1); labelAsteroids.setScaleY(1);}

        });//end of Keyframe

        timelineAsteroidsLabel.getKeyFrames().add(keyFrameTimelineAsteroidsLabel);
        timelineAsteroidsLabel.play();

    }//end of StartAnimationAsteroidsLabel

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //insert the GUI elements in the corresponding arrays
        startScreenElements[0] = buttonStartGame;
        startScreenElements[1] = labelAsteroids;





        //play initial label animation
        StartAnimationAsteroidsLabel();




    }//end of initialize
}//end of GUI