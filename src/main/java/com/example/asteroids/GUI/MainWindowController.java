package com.example.asteroids.GUI;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.PlayerPackage.Healthbar;
import com.example.asteroids.Weapons.Bullet;
import com.example.asteroids.PlayerPackage.Player;
import com.example.asteroids.Weapons.Laser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainWindowController implements Initializable {

    //Fields
    private Parent[] startScreenElements = new Parent[4];
    private GraphicsContext gc;
    private long elapsedTime = 0;
    private long elapsedTimeShotable = 0;
    private long elapsedTimeLaser = 0;
    private final long spawnInterval = 50;
    private Player player;
    private Timeline gameloop;
    private Healthbar playerHealthbar;

    //static fields
    private static boolean gameRunning = false;

    // Flags to track key states
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;
    private boolean shoot = false;

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
    private Button buttonBackToMenu;
    @FXML
    private Button journalButton;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView pressToPlay1;
    @FXML
    private ImageView pressToPlay2;
    @FXML
    private ImageView pressToPlay3;
    @FXML
    private Label pressToPlayLabel;
    @FXML
    private Label defeatLabel;
    @FXML
    private Label pointsLabel;
    @FXML
    private Label labelAutofire;

    //---------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private void onButtonJournal() {
        try {
            // Load the journal FXML file from the same package
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/asteroids/JournalWindow.fxml"));
            Parent root = (loader.load());

            // Create the new stage
            Stage mainStage = (Stage) journalButton.getScene().getWindow();
            Stage journalStage = new Stage();
            journalStage.setTitle("Journal");
            Scene journalScene = new Scene(root);
            journalStage.setHeight(mainStage.getHeight()/2.2);
            journalStage.setWidth(mainStage.getWidth()/2.2);
            journalScene.setFill(Color.TRANSPARENT);
            journalStage.setScene(journalScene);
            journalStage.initStyle(StageStyle.TRANSPARENT);
            journalStage.setX(mainStage.getX() + (mainStage.getWidth() - journalStage.getWidth())/2);
            journalStage.setY(mainStage.getY() + (mainStage.getHeight() - journalStage.getHeight())/2);



            journalStage.initOwner(mainStage);
            journalStage.initModality(Modality.WINDOW_MODAL);

            journalStage.setResizable(false);
            journalStage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


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
        pressToPlay3.setVisible(false);


        //attach the playerObject to the bullets
        player.attachHealthBar(playerHealthbar);
        Bullet.attachPlayer(player);


        //reset player
        player.setHealthPoints(100);


        pointsLabel.setVisible(true);
        labelAutofire.setVisible(true);

        //start the game
        gameRunning = true;
        Main();

    }//end of onButtonStartGame
    @FXML
    private void onButtonBackToMenu(){

        //stop the game
        buttonBackToMenu.setVisible(false);
        defeatLabel.setVisible(false);
        player.setPoints(0);
        pointsLabel.setVisible(false);
        gameRunning = false;

        //change player visuals back to default
        player.setCoordX(700.0);
        player.setCoordY(334.0);
        player.getImageView().setX(700);
        player.getImageView().setY(334);
        player.getImageView().setRotate(0);
        Image image = new Image(getClass().getResource( "/imgs/SpaceshipPlayer.png").toExternalForm());
        player.getImageView().setImage(image);


        //show the startscreen
        for(javafx.scene.Node element : startScreenElements){
            element.setVisible(true);
        }
        pressToPlay1.setVisible(true);
        pressToPlay2.setVisible(true);
        pressToPlay3.setVisible(true);


    }//end of onButtonBackToMenu
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
            case SPACE:
                shoot = true;
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
            case SPACE:
                shoot = false;
                break;
            case R:
                shoot = !shoot;
            default:
                break;
        }
    }

    public static void setGameRunning(boolean gR) {gameRunning = gR;}

    // we want to show some kind of lost screen on the gui and reset all the objects(asteroids) created
    private void lost(){

        if(!gameRunning){

            //stop the game
            gameloop.stop();


            //swap the spaceship with an explosion
            player.getImageView().setImage(new Image(getClass().getResource("/imgs/Explosion.png").toExternalForm()));


            //unalive all the asteroids existing
            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){

                asteroid.removeImage();
                asteroid.despawnAsteroid();

            }//end of for

            //unalive all the bullets
            player.setBullets(new Stack<>());
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


            Laser.getInstance().resetLaser();
            buttonBackToMenu.setVisible(true);
            exitButton.setVisible(true);
            defeatLabel.setVisible(true);


        }//end of if

    }//end of lost

    //main method/loop/u know
    private void Main(){

        gc = canvas.getGraphicsContext2D();

        mainAnchorPane.setFocusTraversable(true);
        mainAnchorPane.requestFocus();

         gameloop = new Timeline(new KeyFrame(Duration.millis(7), event -> {

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            pointsLabel.setText("Points : "+player.getPoints());

            //check for all the bullets to be despawned by time
            Bullet.despawnBulletByTime();


             for (Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())) {
                 if (asteroid.checkDespawn()) {
                     mainAnchorPane.getChildren().remove(asteroid.getAsteroidImage());
                 }
             }
            //check for collision and uncomment the stroke to see the asteroids hit boxes for debugging purpose
            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){

                if(player.checkCollision(asteroid.getAsteroidImage())){
                    lost();
                }
            /*
               gc.strokeRect(asteroid.getAsteroidImage().getBoundsInParent().getMinX() , asteroid.getAsteroidImage().getBoundsInParent().getMinY()
                              ,asteroid.getAsteroidImage().getBoundsInParent().getWidth() , asteroid.getAsteroidImage().getBoundsInParent().getHeight());
            */
            }

            elapsedTime+= 7;
            elapsedTimeShotable += 7;
            elapsedTimeLaser += 7;


            if(elapsedTime >= spawnInterval ){

                Asteroid.spawnAsteroid();
                mainAnchorPane.getChildren().add(Asteroid.getAsteroids().getFirst().getAsteroidImage());


                if(elapsedTime >= spawnInterval * 2 ){
                    mainAnchorPane.getChildren().remove(Asteroid.getAsteroids().getLast().getAsteroidImage());
                    Asteroid.getAsteroids().removeLast();
                }

                elapsedTime = 0;
            }

            for(Bullet bullet : player.getBullets()){
                bullet.moveBullet();
            }



            //draw the bullets using the javafx canvas
             for (Bullet bullet : new ArrayList<>(player.getBullets())) {drawBullet(bullet); bullet.checkCollision();}

            drawHealthBar();



            Asteroid.moveAsteroid();


            if(elapsedTimeLaser >= Laser.getInstance().getShootableInterval()){
                if(Laser.getInstance().isShootable()) {
                    Laser.getInstance().shoot();
                    drawLaser();
                    elapsedTimeLaser = 0;
                }

            }

            if(shoot){
                if(elapsedTimeShotable >= Bullet.getShootableInterval()){
                    Bullet.spawnBullet();
                    elapsedTimeShotable = 0;

                }
            }
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


             if(player.getCoordX() < 0){player.setCoordX(0);player.getImageView().setX(0);}
             if(player.getCoordY() < 0){player.setCoordY(0);player.getImageView().setY(0);}
             if(player.getCoordX()  > 1450){player.setCoordX(1450);player.getImageView().setX(1450);}
             if(player.getCoordY()  > 750){player.setCoordY(750);player.getImageView().setY(750);}







        }));

        gameloop.setCycleCount(Timeline.INDEFINITE);
        gameloop.setAutoReverse(true);
        gameloop.play();

    }//end of Main


    private void drawLaser(){

        double startX = player.getCoordX()+player.getWidth()/2-100;
        double startY = player.getCoordY()+player.getHeight()/2;
        double length = Laser.getInstance().getLength();
        double width = Laser.getInstance().getWidth();
        double angle = Laser.getInstance().getAngle();


        double endX = startX + length * Math.cos(Math.toRadians(angle));
        double endY = startY + length * Math.sin(Math.toRadians(angle));

        // Draw the rotated rectangle as a polygon
        gc.setStroke(Laser.getInstance().getLaserColor());
        gc.setLineWidth(Laser.getInstance().getWidth());
        gc.strokeLine(startX,startY,endX,endY);

    }



    private void drawHealthBar(){
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(player.getHealthBar().getCoordX()+20,player.getHealthBar().getCoordY()-20, player.getHealthPoints() ,30);
    }//end of drawHealthBar

    //logic to draw a Bullet
    private void drawBullet(Bullet bullet) {
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillOval(bullet.getCoordX(), bullet.getCoordY(), bullet.getRadius() , bullet.getRadius());

    }//end of drawBullet

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
        player = new Player(10000,playerShip);
        player.attachHealthBar(playerHealthbar);

        playerHealthbar = new Healthbar();



        Laser.getInstance().attachPlayer(player);



        //insert the GUI elements in the corresponding arrays
        startScreenElements[0] = buttonStartGame;
        startScreenElements[1] = labelAsteroids;
        startScreenElements[2] = exitButton;
        startScreenElements[3] = pressToPlayLabel;


        //play initial label animation the call also starts the spawning etc....
        StartAnimationAsteroidsLabel();

    }//end of initialize

}//end of GUI