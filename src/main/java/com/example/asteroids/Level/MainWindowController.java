package com.example.asteroids.Level;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Asteroids.Disruptor;
import com.example.asteroids.Asteroids.DualityCores;
import com.example.asteroids.Descriptions.DescriptionCredits;
import com.example.asteroids.Player.Healthbar;
import com.example.asteroids.SoundHandling.MusicPlayer;
import com.example.asteroids.Transaction.PlayerCurrencyHandler;
import com.example.asteroids.Weapons.Bullet;
import com.example.asteroids.Player.Player;
import com.example.asteroids.Weapons.Laser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.HostServices;
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
import javafx.scene.paint.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;


public class MainWindowController implements Initializable  {

    private final Parent[] startScreenElements = new Parent[5];
    private GraphicsContext gc;
    private long elapsedTime = 0;
    private long elapsedTimeShotable = 0;
    private long elapsedTimeLaser = 0;
    private final long SPAWN_INTERVAL = 25;
    private Player player;
    private Timeline gameloop;
    private Healthbar playerHealthbar;
    private long damageImageStartTime = 0;
    private static long stunStartTime = 0;
    private final long DAMAGE_IMAGE_DURATION = 100;
    private final String LOBBY_BACKGROUND_SOUND = "src/main/resources/Sounds/Audios/LobbyBackgroundSound.mp3";
    private final String OVERWOWRLD_BACKGROUND_SOUND = "src/main/resources/Sounds/Audios/Ingame.mp3";
    private final MusicPlayer lobbyMusicPlayer = new MusicPlayer(LOBBY_BACKGROUND_SOUND);
    private final MusicPlayer overworldMusicPlayer = new MusicPlayer(OVERWOWRLD_BACKGROUND_SOUND);
    private static boolean gameRunning = false;
    private boolean isInHitBoxMode = false;
    private boolean settingsVisible = false;
    private static HostServices hostServices;

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
    private Button backToMenuButton;
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
    @FXML
    private AnchorPane settingsOverlay;



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
        overworldMusicPlayer.playSound();


        //attach the playerObject to the bullets
        player.attachHealthBar(playerHealthbar);
        Bullet.attachPlayer(player);

        //reset player
        player.setHealthPoints(100000);

        currencyLabel.setVisible(true);
        labelAutofire.setVisible(true);

        //start the game
        gameRunning = true;
        Main();

    }
    @FXML
    private void onButtonBackToMenu(){

        //stop the game
        backToMenuButton.setVisible(false);
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

    }
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
                break;
            case F3:
                isInHitBoxMode = !isInHitBoxMode;
                break;
            case ESCAPE:
                if (gameRunning) {
                    onSettingsToggle();
                }
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

            gameloop.stop();
            overworldMusicPlayer.stopSound();

            //swap the spaceship with an explosion
            player.getImageView().setImage(new Image(getClass().getResource("/imgs/Explosion.png").toExternalForm()));

            //unalive all the asteroids existing
            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
                asteroid.removeImage();
                asteroid.despawnAsteroid();
            }

            //unalive all the bullets
            player.setBullets(new Stack<>());
            Laser.getInstance().resetLaser();
            backToMenuButton.setVisible(true);
            exitButton.setVisible(true);
            defeatLabel.setVisible(true);
            labelAutofire.setVisible(false);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        }

    }

    private void Main(){

        gc = canvas.getGraphicsContext2D();

        mainAnchorPane.setFocusTraversable(true);
        mainAnchorPane.requestFocus();

        gameloop = new Timeline(new KeyFrame(Duration.millis(16), event -> {

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            currencyLabel.setText("SpaceCoins : "+ PlayerCurrencyHandler.getPlayerSpaceCoins());



            drawHealthBar();

            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
                asteroid.move();
            }

            //check for all the bullets to be despawned by time
            Bullet.despawnBulletByTime();

            if(isInHitBoxMode){
                gc.setStroke(Color.BLUE);
                gc.strokeRect(player.getImageView().getBoundsInParent().getMinX(),
                        player.getImageView().getBoundsInParent().getMinY(),
                        player.getImageView().getBoundsInParent().getWidth(),
                        player.getImageView().getBoundsInParent().getHeight());
            }

            if(System.currentTimeMillis() - stunStartTime >= Disruptor.getStunDuration()){
                player.setStunned(false);
                stunStartTime = 0;
            }

            // Improved damage image reset logic
            if(player.getDamageState()){
                // If damage just started, record the start time
                if(damageImageStartTime == 0) {
                    damageImageStartTime = System.currentTimeMillis();
                }

                // Check if enough time has passed to reset the image
                if(System.currentTimeMillis() - damageImageStartTime >= DAMAGE_IMAGE_DURATION) {
                    player.resetDamageImage();
                    damageImageStartTime = 0;
                }
            } else {
                // Reset timer if player is not in damage state
                damageImageStartTime = 0;
            }

            for (Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())) {
                if(isInHitBoxMode) {
                    if(asteroid instanceof Disruptor) {
                        gc.setStroke(Color.GREEN);
                        gc.strokeRect(asteroid.getAsteroidImage().getBoundsInParent().getMinX() - 15,
                                asteroid.getAsteroidImage().getBoundsInParent().getMinY() - 15,
                                asteroid.getAsteroidImage().getFitWidth() + 30,
                                asteroid.getAsteroidImage().getFitHeight() + 30);
                        gc.setStroke(Color.YELLOW);
                        gc.strokeRect(asteroid.getAsteroidImage().getBoundsInParent().getMinX(),
                                asteroid.getAsteroidImage().getBoundsInParent().getMinY(),
                                asteroid.getAsteroidImage().getFitWidth(),
                                asteroid.getAsteroidImage().getFitHeight());
                    }else{
                        gc.strokeRect(asteroid.getAsteroidImage().getBoundsInParent().getMinX(),
                                asteroid.getAsteroidImage().getBoundsInParent().getMinY(),
                                asteroid.getAsteroidImage().getFitWidth(),
                                asteroid.getAsteroidImage().getFitHeight());
                    }
                }
                if (asteroid.checkDespawn()) {
                    mainAnchorPane.getChildren().remove(asteroid.getAsteroidImage());
                }
            }

            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
                try {
                    if(player.checkCollision(asteroid.getAsteroidImage(),asteroid)){
                        lost();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            elapsedTime+= 7;
            elapsedTimeShotable += 7;
            elapsedTimeLaser += 7;

            if(elapsedTime >= SPAWN_INTERVAL){

                Asteroid.spawnAsteroid();
                Asteroid firstAsteroid = Asteroid.getAsteroids().getFirst();
                if(!mainAnchorPane.getChildren().contains(firstAsteroid.getAsteroidImage())) {
                    mainAnchorPane.getChildren().add(firstAsteroid.getAsteroidImage());
                }

                if(firstAsteroid instanceof DualityCores){
                    DualityCores dualityCores = (DualityCores) firstAsteroid;
                    if(!mainAnchorPane.getChildren().contains(dualityCores.getSecondaryCore().getAsteroidImage())) {
                        mainAnchorPane.getChildren().add(dualityCores.getSecondaryCore().getAsteroidImage());
                    }
                }

                elapsedTime = 0;
            }

            for(Bullet bullet : player.getBullets()){
                if(isInHitBoxMode) {
                    gc.setStroke(Color.RED);
                    gc.strokeRect(bullet.getCoordX(), bullet.getCoordY(), bullet.getRadius(), bullet.getRadius());
                }
                bullet.moveBullet();
            }

            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
                if(asteroid instanceof DualityCores && ((DualityCores) asteroid).isMainCore()){
                    DualityCores dualityCore = (DualityCores) asteroid;
                    if(dualityCore.getSecondaryCore() != null) {
                        double[] tetherCoords = dualityCore.getTetherCoords();
                        drawDualityCoresTether(tetherCoords[0], tetherCoords[1], tetherCoords[2], tetherCoords[3]);
                    }
                }else if(asteroid instanceof Disruptor){
                    drawEMP((Disruptor) asteroid);
                }
            }

            for (Bullet bullet : new ArrayList<>(player.getBullets())) {
                drawBullet(bullet);
                try {
                    bullet.checkCollision();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

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
            if(!player.isStunned()) {
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
            }

            if(player.getCoordX()<0){
                player.setCoordX(0);
                player.getImageView().setX(0);
            }
            if(player.getCoordY()<0){
                player.setCoordY(0);
                player.getImageView().setY(0);
            }
            if(player.getCoordX()>1450){
                player.setCoordX(1450);
                player.getImageView().setX(1450);
            }
            if(player.getCoordY()>750){
                player.setCoordY(750);
                player.getImageView().setY(750);
            }

        }));

        gameloop.setCycleCount(Timeline.INDEFINITE);
        gameloop.play();

    }

    private void drawEMP(Disruptor disruptor) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        // Mittelpunkt = Position des Disruptors
        double cx = disruptor.getAsteroidImage().getX() + 30;
        double cy = disruptor.getAsteroidImage().getY() + 30;

        // Zeit (für Animation)
        double time = (System.nanoTime() / 1e9) % 1000;

        // EMP-Wellen-Effekt
        double maxR = 120;                     // Reichweite des EMP
        double speed = 1.5;                    // Wellen pro Sekunde
        double phase = (time * speed) % 1.0;   // 0..1
        double r = maxR * phase;               // aktueller Radius

        // Transparenz nimmt mit Phase ab
        double alpha = 1.0 - phase;

        // Farbverlauf für die Schockwelle
        RadialGradient pulse = new RadialGradient(
                0, 0, cx, cy, r, false, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web("#66d3ff", alpha)),
                new Stop(0.5, Color.web("#33bbff", alpha * 0.6)),
                new Stop(1.0, Color.TRANSPARENT)
        );

        g.setFill(pulse);
        g.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    private void drawDualityCoresTether(double mainCoreX, double mainCoreY,
                                        double secondCoreX, double secondCoreY){

        // Center the tether on the cores (assuming cores are small)
        double startX = mainCoreX + 25; // Adjust based on your core size
        double startY = mainCoreY + 25;
        double endX = secondCoreX + 25;
        double endY = secondCoreY + 25;

        // Create energy effect colors
        Color coreBlue = Color.CYAN;
        Color glowBlue = Color.DODGERBLUE.deriveColor(0, 1, 1.2, 0.6);
        Color outerGlow = Color.LIGHTBLUE.deriveColor(0, 1, 0.8, 0.3);

        // Time-based effects for energy appearance
        double time = System.nanoTime() / 1_000_000_000.0; // Convert to seconds
        double pulse = 0.7 + 0.3 * Math.sin(time * 4); // Pulsing effect
        double flicker = 0.8 + Math.random() * 0.4; // Random flicker

        // Draw outer glow (widest, most transparent)
        gc.setStroke(outerGlow);
        gc.setLineWidth(8 * pulse);
        gc.strokeLine(startX, startY, endX, endY);

        // Draw main glow
        gc.setStroke(glowBlue);
        gc.setLineWidth(5 * pulse * flicker);
        gc.strokeLine(startX, startY, endX, endY);

        // Create gradient for core beam
        Stop[] stops = new Stop[]{
                new Stop(0, coreBlue.brighter()),
                new Stop(0.5, coreBlue),
                new Stop(1, coreBlue.darker())
        };
        LinearGradient gradient = new LinearGradient(
                startX, startY, endX, endY,
                false, CycleMethod.NO_CYCLE, stops
        );

        // Draw core beam with gradient
        gc.setStroke(gradient);
        gc.setLineWidth(3 * pulse);
        gc.strokeLine(startX, startY, endX, endY);

        // Add sharp inner core line
        gc.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.9));
        gc.setLineWidth(1 * flicker);
        gc.strokeLine(startX, startY, endX, endY);


    }


    private void drawLaser() {
        double startX = player.getCoordX() + (player.getWidth() / 2) + 40;
        double startY = player.getCoordY() + (player.getHeight() / 2) + 40;
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

        });

        timelineAsteroidsLabel.getKeyFrames().add(keyFrameTimelineAsteroidsLabel);
        timelineAsteroidsLabel.play();

    }



    @FXML
    private void onSettingsToggle() {
        if (settingsVisible) {
            overworldMusicPlayer.unpauseSound();
            hideSettingsOverlay();
        } else {
            overworldMusicPlayer.pauseSound();
            showSettingsOverlay();
        }
    }

    private void showSettingsOverlay() {
        // Create settings overlay if it doesn't exist
        if (settingsOverlay == null) {
            createSettingsOverlay();
        }

        // Add overlay to main pane if not already added
        if (!mainAnchorPane.getChildren().contains(settingsOverlay)) {
            mainAnchorPane.getChildren().add(settingsOverlay);
        }

        // Position overlay off-screen (right side)
        settingsOverlay.setLayoutX(mainAnchorPane.getWidth());
        settingsOverlay.setVisible(true);

        // Create slide-in animation
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new javafx.animation.KeyValue(settingsOverlay.layoutXProperty(),
                                mainAnchorPane.getWidth() - 400) // 400px wide panel
                )
        );

        // Pause game if running
        if (gameRunning && gameloop != null) {
            gameloop.pause();
        }

        slideIn.play();
        settingsVisible = true;
    }

    private void hideSettingsOverlay() {
        // Create slide-out animation
        Timeline slideOut = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new javafx.animation.KeyValue(settingsOverlay.layoutXProperty(),
                                mainAnchorPane.getWidth())
                )
        );


        slideOut.setOnFinished(e -> {
            settingsOverlay.setVisible(false);
            // Resume game if it was running
            if (gameRunning && gameloop != null) {
                gameloop.play();
                mainAnchorPane.requestFocus(); // Return focus to game
            }
        });

        slideOut.play();
        settingsVisible = false;
        overworldMusicPlayer.unpauseSound();
    }

    private void createSettingsOverlay() {
        settingsOverlay = new AnchorPane();
        settingsOverlay.setPrefWidth(400);
        settingsOverlay.setPrefHeight(mainAnchorPane.getHeight());

        // Semi-transparent dark background
        settingsOverlay.setStyle(
                "-fx-background-color: rgba(0, 20, 40, 0.95); " +
                        "-fx-border-color: #00FFFF; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-border-radius: 10px;"
        );

        // Settings title
        Label titleLabel = new Label("SETTINGS");
        titleLabel.setStyle(
                "-fx-text-fill: #00FFFF; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;"
        );
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(30);

        // Close button (X)
        Button closeButton = new Button("×");
        closeButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #FF4444; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold;"
        );
        closeButton.setLayoutX(360);
        closeButton.setLayoutY(20);
        closeButton.setOnAction(e -> hideSettingsOverlay());


        Label creditsLabelFastsneak = new Label(DescriptionCredits.getDescriptionFastSneak());
        creditsLabelFastsneak.setStyle(
                "-fx-text-fill: #00FFFF; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold;"
        );

        creditsLabelFastsneak.setOnMouseClicked(e -> {
            hostServices.showDocument("https://barbermessedup.netlify.app");
        });
        creditsLabelFastsneak.setLayoutX(20);
        creditsLabelFastsneak.setLayoutY(500);


        Label creditsLabelTirox67 = new Label(DescriptionCredits.getDescriptionTirox67());
        creditsLabelTirox67.setStyle(
                "-fx-text-fill: #00FFFF; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold;"
        );

        creditsLabelTirox67.setOnMouseClicked(e -> {
            hostServices.showDocument("https://github.com/TimUrbanczyk");
        });
        creditsLabelTirox67.setLayoutX(20);
        creditsLabelTirox67.setLayoutY(520);




        // Sample settings controls
        createSettingsControls(settingsOverlay);

        settingsOverlay.getChildren().addAll(titleLabel, closeButton, creditsLabelFastsneak, creditsLabelTirox67);
    }

    private void createSettingsControls(AnchorPane overlay) {
        double yOffset = 80;
        double spacing = 50;

        // Volume Control
        Label volumeLabel = new Label("Master Volume:");
        volumeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        volumeLabel.setLayoutX(30);
        volumeLabel.setLayoutY(yOffset);

        javafx.scene.control.Slider volumeSlider = new javafx.scene.control.Slider(0, 1, 0.5);
        volumeSlider.setLayoutX(30);
        volumeSlider.setLayoutY(yOffset + 25);
        volumeSlider.setPrefWidth(200);
        volumeSlider.setStyle("-fx-control-inner-background: #00FFFF;");


        // Controls section
        yOffset += spacing;
        Label controlsLabel = new Label("Controls:");
        controlsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        controlsLabel.setLayoutX(30);
        controlsLabel.setLayoutY(yOffset);

        // Show current key bindings
        String[] controls = {"Move Up: W", "Move Down: S", "Move Left: A",
                "Move Right: D", "Shoot: SPACE", "Auto-fire: R"};

        for (int i = 0; i < controls.length; i++) {
            Label controlLabel = new Label(controls[i]);
            controlLabel.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 12px;");
            controlLabel.setLayoutX(50);
            controlLabel.setLayoutY(yOffset + 30 + (i * 20));
            overlay.getChildren().add(controlLabel);
        }

        // Gameplay settings
        yOffset += spacing + 140;
        javafx.scene.control.CheckBox hitboxToggle = new javafx.scene.control.CheckBox("Show Hitboxes (F3)");
        hitboxToggle.setStyle("-fx-text-fill: white;");
        hitboxToggle.setLayoutX(30);
        hitboxToggle.setLayoutY(yOffset);
        hitboxToggle.setSelected(isInHitBoxMode);
        hitboxToggle.setOnAction(e -> isInHitBoxMode = hitboxToggle.isSelected());

        // Apply button
        yOffset += spacing;
        Button applyButton = new Button("APPLY SETTINGS");
        applyButton.setStyle(
                "-fx-background-color: #00FFFF; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5px;"
        );
        applyButton.setLayoutX(30);
        applyButton.setLayoutY(yOffset);
        applyButton.setPrefWidth(150);
        applyButton.setOnAction(e -> {
            // Apply settings logic here
            MusicPlayer.setVolume(volumeSlider.getValue());
            hideSettingsOverlay();
        });

        Button backToMenuButton = new Button("BACK TO MENU");
        backToMenuButton.setStyle(
                "-fx-background-color: #00FFFF; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5px;"
        );
        backToMenuButton.setLayoutX(30);
        backToMenuButton.setLayoutY(yOffset + 40);
        backToMenuButton.setPrefWidth(150);
        backToMenuButton.setOnAction(e -> {

            gameloop.stop();
            overworldMusicPlayer.stopSound();

            for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
                asteroid.removeImage();
                asteroid.despawnAsteroid();
            }

            //unalive all the bullets
            player.setBullets(new Stack<>());
            Laser.getInstance().resetLaser();
            labelAutofire.setVisible(false);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            hideSettingsOverlay();
            onButtonBackToMenu();
            overworldMusicPlayer.stopSound();
        });
        
        overlay.getChildren().addAll(
                volumeLabel, volumeSlider,
                controlsLabel, hitboxToggle,
                applyButton, backToMenuButton
        );
    }

    public static void setStunStartTime(long time){
        stunStartTime = time;
    }

    public static void setHostServices(HostServices hostService) {
        hostServices = hostService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Create Player
        player = new Player(0,playerShip);
        player.attachHealthBar(playerHealthbar);
        Disruptor.attachPlayer(player);
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

        overworldMusicPlayer.setRepeat();

    }

}