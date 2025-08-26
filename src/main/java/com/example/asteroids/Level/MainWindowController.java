package com.example.asteroids.Level;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.BossFights.InfernoidFightController;
import com.example.asteroids.Player.Healthbar;
import com.example.asteroids.SoundHandling.MusicPlayer;
import com.example.asteroids.Transaction.PlayerCurrencyHandler;
import com.example.asteroids.Weapons.Bullet;
import com.example.asteroids.Player.Player;
import com.example.asteroids.Weapons.Laser;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;


public class MainWindowController implements Initializable {

    //Fields
    private final Parent[] startScreenElements = new Parent[5];
    private GraphicsContext gc;
    private long elapsedTime = 0;
    private long elapsedTimeShotable = 0;
    private long elapsedTimeLaser = 0;
    private final long spawnInterval = 50;
    private Player player;
    private Timeline gameloop;
    private Healthbar playerHealthbar;
    private final int thresholdInfernoidFight = 1000;
    // Improved damage image reset logic
    private long damageImageStartTime = 0;
    private final long damageImageDuration = 100;
    private final MusicPlayer lobbyMusicPlayer = new MusicPlayer("src/main/resources/Sounds/Audios/LobbyBackgroundSound.mp3");
    //static fields
    private static boolean gameRunning = false;
    private boolean infernoidFightStarted = false;

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
    private Label labelOrbitbreaker = LabelOrbitbreaker.getInstance().getLabelOrbitbreaker();
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
    private Label currencyLabel;
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
            journalStage.setX(mainStage.getX() + (mainStage.getWidth() - journalStage.getWidth())/2);
            journalStage.setY(mainStage.getY() + (mainStage.getHeight() - journalStage.getHeight())/2);


            journalStage.initOwner(mainStage);
            journalStage.initModality(Modality.WINDOW_MODAL);
            journalStage.initStyle(StageStyle.UNDECORATED);
            journalStage.initStyle(StageStyle.TRANSPARENT);


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

        //stop lobby sound
        lobbyMusicPlayer.stopSound();


        //attach the playerObject to the bullets
        player.attachHealthBar(playerHealthbar);
        Bullet.attachPlayer(player);


        //reset player
        player.setHealthPoints(100);


        currencyLabel.setVisible(true);
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
        currencyLabel.setVisible(false);
        gameRunning = false;

        //change player visuals back to default
        player.setCoordX(700.0);
        player.setCoordY(334.0);
        player.getImageView().setX(700);
        player.getImageView().setY(334);
        player.getImageView().setRotate(0);
        Image image = new Image(getClass().getResource( "/imgs/SpaceshipPlayer.png").toExternalForm());
        player.getImageView().setImage(image);


        lobbyMusicPlayer.playSound();

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
            labelAutofire.setVisible(false);


        }//end of if

    }//end of lost

    //main method/loop/u know
    private void Main(){

        gc = canvas.getGraphicsContext2D();

        mainAnchorPane.setFocusTraversable(true);
        mainAnchorPane.requestFocus();

        gameloop = new Timeline(new KeyFrame(Duration.millis(16), event -> {

            List<Asteroid> asteroidList = new ArrayList<>(Asteroid.getAsteroids());

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            currencyLabel.setText("SpaceCoins : "+ PlayerCurrencyHandler.getPlayerSpaceCoins());


            // Trigger Infernoid fight when player reaches 1000 points (only once)
            if(PlayerCurrencyHandler.getPlayerSpaceCoins() >= thresholdInfernoidFight && !infernoidFightStarted){

                try {
                    infernoidFightStarted = true;
                    startInfernoidFight();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //check for all the bullets to be despawned by time
            Bullet.despawnBulletByTime();

            // Improved damage image reset logic
            if(player.getDamageState()){
                // If damage just started, record the start time
                if(damageImageStartTime == 0) {
                    damageImageStartTime = System.currentTimeMillis();
                }

                // Check if enough time has passed to reset the image
                if(System.currentTimeMillis() - damageImageStartTime >= damageImageDuration) {
                    player.resetDamageImage();
                    damageImageStartTime = 0; // Reset the timer
                }
            } else {
                // Reset timer if player is not in damage state
                damageImageStartTime = 0;
            }


            for (Asteroid asteroid : asteroidList) {
                if (asteroid.checkDespawn()) {
                    mainAnchorPane.getChildren().remove(asteroid.getAsteroidImage());
                }
            }
            //check for collision and uncomment the stroke to see the asteroids hit boxes for debugging purpose
            for(Asteroid asteroid : asteroidList){

                if(player.checkCollision(asteroid.getAsteroidImage())){
                    lost();
                }

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
        //gameloop.setAutoReverse(true);
        gameloop.play();

    }//end of Main


    private void drawLaser() {
        double startX = player.getCoordX() + player.getWidth() / 2;
        double startY = player.getCoordY() + player.getHeight() / 2;
        double length = Laser.getInstance().getLength();
        double baseWidth = Laser.getInstance().getWidth();
        double angle = Laser.getInstance().getAngle();

        double endX = startX + length * Math.cos(Math.toRadians(angle));
        double endY = startY + length * Math.sin(Math.toRadians(angle));

        // Colors
        Color coreColor = Laser.getInstance().getLaserColor(); // base color
        Color glowColor = coreColor.deriveColor(0, 1, 1.5, 0.4); // brighter, transparent
        Color outerGlow = Color.rgb(0, 255, 255, 0.15); // subtle cyan haze

        // Time-based flicker
        double flicker = 0.9 + Math.random() * 0.2; // random intensity factor
        double pulse = 0.75 + 0.25 * Math.sin(System.nanoTime() / 80_000_000.0); // smooth pulse

        // Outer haze (widest, faint)
        gc.setStroke(outerGlow);
        gc.setLineWidth(baseWidth * 3 * pulse);                                             
        gc.strokeLine(startX, startY, endX, endY);

        // Main glow
        gc.setStroke(glowColor);
        gc.setLineWidth(baseWidth * 1.8 * pulse * flicker);
        gc.strokeLine(startX, startY, endX, endY);

        // Gradient core
        Stop[] stops = new Stop[]{
                new Stop(0, coreColor.brighter()),
                new Stop(0.5, coreColor),
                new Stop(1, coreColor.darker())
        };
        LinearGradient gradient = new LinearGradient(
                startX, startY, endX, endY,
                false, CycleMethod.NO_CYCLE, stops
        );
        gc.setStroke(gradient);
        gc.setLineWidth(baseWidth * pulse);
        gc.strokeLine(startX, startY, endX, endY);

        // Add a sharper inner streak for energy core
        gc.setStroke(coreColor.deriveColor(0, 1, 2, 1));
        gc.setLineWidth(baseWidth * 0.4 * flicker);
        gc.strokeLine(startX, startY, endX, endY);
    }





    private void drawHealthBar() {
        gc = canvas.getGraphicsContext2D();

        // Position and size
        double x = player.getHealthBar().getCoordX() + 20;
        double y = player.getHealthBar().getCoordY() - 20;
        double width = 200; // max width of the health bar
        double height = 30;

        double healthRatio = Math.max(0, Math.min(1, player.getHealthPoints() / 100.0));
        double currentWidth = width * healthRatio;

        // Background (empty health)
        gc.setFill(Color.rgb(20, 20, 30)); // dark space grey
        gc.fillRoundRect(x, y, width, height, 10, 10);

        // Gradient for health fill
        Color startColor = Color.GREEN.interpolate(Color.RED, 1 - healthRatio);
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, startColor.brighter()),
                new Stop(1, startColor.darker())
        );
        gc.setFill(gradient);
        gc.fillRoundRect(x, y, currentWidth, height, 10, 10);

        // Border
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(2);
        gc.strokeRoundRect(x, y, width, height, 10, 10);

        // Glow effect (optional space vibe)
        gc.setStroke(Color.CYAN);
        gc.setLineWidth(1);
        gc.strokeRoundRect(x - 2, y - 2, width + 4, height + 4, 12, 12);
    }

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

            double currentScaleX = labelOrbitbreaker.getScaleX();
            double currentScaleY = labelOrbitbreaker.getScaleY();

            if(currentScaleX <= 1.5 && currentScaleY <= 1.5) {labelOrbitbreaker.setScaleX(currentScaleX + 0.01); labelOrbitbreaker.setScaleY(currentScaleY + 0.01);}
            else{labelOrbitbreaker.setScaleX(1); labelOrbitbreaker.setScaleY(1);}

        });//end of Keyframe

        timelineAsteroidsLabel.getKeyFrames().add(keyFrameTimelineAsteroidsLabel);
        timelineAsteroidsLabel.play();

    }//end of StartAnimationAsteroidsLabel


    private void startInfernoidFight() throws IOException {

        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();

        // Check if stage is null and try alternative ways to get it
        if (stage == null) {
            stage = (Stage) buttonStartGame.getScene().getWindow();
        }
        if (stage == null) {
            stage = (Stage) canvas.getScene().getWindow();
        }

        // If still null, we can't proceed
        if (stage == null) {
            System.err.println("Could not get stage reference");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/asteroids/InfernoidFight.fxml"));
        Parent root = loader.load();

        InfernoidFightController controller = loader.getController();

        Scene scene = new Scene(root, 1600, 900);
        stage.setScene(scene);

        // Create smooth fade transition for stage change
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);

        Stage finalStage = stage;
        fadeOut.setOnFinished(event -> {
            // Set the new scene after fade out
            finalStage.setScene(scene);

            controller.main(); // Start the game loop
            controller.InfernoidAnchorPane.setFocusTraversable(true);
            controller.InfernoidAnchorPane.requestFocus();

            // Create smooth fade in transition for the new scene
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), scene.getRoot());
            fadeIn.setFromValue(0.7);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();

        Asteroid.disableSpawn();
        Asteroid.despawnAll();

        InfernoidFightController.attachPlayer(player);

        mainAnchorPane.setFocusTraversable(false);


        gameloop.pause();



    }



    //init
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Create Player
        player = new Player(0,playerShip);
        player.attachHealthBar(playerHealthbar);

        playerHealthbar = new Healthbar();



        Laser.getInstance().attachPlayer(player);



        //insert the GUI elements in the corresponding arrays
        startScreenElements[0] = buttonStartGame;
        startScreenElements[1] = labelOrbitbreaker;
        startScreenElements[2] = exitButton;
        startScreenElements[3] = pressToPlayLabel;
        startScreenElements[4] = journalButton;


        //play initial label animation the call also starts the spawning etc....
        StartAnimationAsteroidsLabel();

        //play lobby music
        lobbyMusicPlayer.setVolume(0.3);
        lobbyMusicPlayer.setRepeat();
        lobbyMusicPlayer.playSound();

    }//end of initialize

}//end of GUI