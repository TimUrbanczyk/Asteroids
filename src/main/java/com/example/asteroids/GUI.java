package com.example.asteroids;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    //Fields
    private Parent[] startScreenElements = new Parent[4];
    private GraphicsContext gc;
    private long elapsedTime = 0;
    private final long spawnInterval = 50;
    private Player player;
    private Timeline gameloop;

    //static fields
    private static boolean gameRunning = false;

    // Flags to track key states
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;

    //FXML fields
    @FXML
    private Label labelAsteroids = LabelAsteroids.getInstance().getLabelAsteroids();
    @FXML
    private Button buttonStartGame;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private ImageView playerShip;
    @FXML
    private Button exitButton;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView pressToPlay1;
    @FXML
    private ImageView pressToPlay2;
    @FXML
    private Label pressToPlayLabel;


    //---------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private void onExitButton(){System.exit(0);}
    @FXML
    private void onButtonStartGame(){

        //vanish all the gui elements from the start screen
        for(javafx.scene.Node element : startScreenElements){
            element.setVisible(false);
        }
        pressToPlay1.setVisible(false);
        pressToPlay2.setVisible(false);

        //start the game
        gameRunning = true;
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

    public static  boolean isGameRunning() {return gameRunning;}
    public static void setGameRunning(boolean gR) {gameRunning = gR;}

    // we want to show some kind of lost screen on the gui and reset all the objects(asteroids) created
    private void lost(){

        if(!gameRunning){
            //stop the game
            gameloop.stop();

            //swap the spaceship with an explosion
            player.getImageView().setImage(new Image("C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\Explosion.png"));
        }

    }//end of lost
    //main method/loop/u know
    private void Main(){

        gc = canvas.getGraphicsContext2D();

        mainAnchorPane.setFocusTraversable(true);
        mainAnchorPane.requestFocus();

         gameloop = new Timeline(new KeyFrame(Duration.millis(7), event -> {

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            //uncomment this bock th see the ships hit box
            // Draw a yellow rectangle
            gc.setStroke(Color.YELLOW);
            // Use playerShip's current position and size
            double x = player.getCoordX(); // Use the ship's position directly
            double y = player.getCoordY();
            double width = player.getWidth();
            double height = player.getHeight();

            // Draw the rectangle
            gc.strokeRect(x, y, width, height);

            //check for collision and uncomment the stroke to see the asteroids hit boxes for debugging purpose
            for(Asteroid asteroid : Asteroid.getAsteroids()){

                if(player.checkCollision(asteroid.getAsteroidImage())){
                    lost();
                }

               gc.strokeRect(asteroid.getAsteroidImage().getBoundsInParent().getMinX() , asteroid.getAsteroidImage().getBoundsInParent().getMinY()
                              ,asteroid.getAsteroidImage().getBoundsInParent().getWidth() , asteroid.getAsteroidImage().getBoundsInParent().getHeight());

            }

            elapsedTime+= 7;

            if(elapsedTime >= spawnInterval ){

                Asteroid.spawnAsteroid();
                mainAnchorPane.getChildren().add(Asteroid.getAsteroids().getFirst().getAsteroidImage());


                if(elapsedTime >= spawnInterval * 2 ){
                    mainAnchorPane.getChildren().remove(Asteroid.getAsteroids().getLast().getAsteroidImage());
                    Asteroid.getAsteroids().removeLast();
                }

                elapsedTime = 0;
            }

            Asteroid.moveAsteroid();

            if (movingUp) {
                player.setCoordY(player.getCoordY() - player.getSpeed());

                playerShip.setY(playerShip.getY() - player.getSpeed());
            }
            if (movingDown) {
                player.setCoordY(player.getCoordY() + player.getSpeed());
                playerShip.setY(playerShip.getY() + player.getSpeed());
            }
            if (movingLeft) {
                player.setCoordX(player.getCoordX() - player.getSpeed());
                playerShip.setX(playerShip.getX() - player.getSpeed());
            }
            if (movingRight) {
                player.setCoordX(player.getCoordX() + player.getSpeed());
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
    //code to start the wonderful animation at the beginning of the game
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
    //init
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Create Player
        player = new Player(100,playerShip);

        //insert the GUI elements in the corresponding arrays
        startScreenElements[0] = buttonStartGame;
        startScreenElements[1] = labelAsteroids;
        startScreenElements[2] = exitButton;
        startScreenElements[3] = pressToPlayLabel;



        //play initial label animation the call also starts the spawning etc....
        StartAnimationAsteroidsLabel();

    }//end of initialize
}//end of GUI