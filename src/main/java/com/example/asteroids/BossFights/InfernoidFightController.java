package com.example.asteroids.BossFights;

import com.example.asteroids.BossFights.Bosses.Infernoid;
import com.example.asteroids.Player.Player;
import com.example.asteroids.Weapons.Bullet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//first bossfight in the game
public class InfernoidFightController implements Initializable {

    private static Player player;
    private static boolean playerAttached = false;

    private Timeline gameloop;
    private final long elapsedTime = 0;
    private long elapsedTimeShotable = 0;
    private final long elapsedTimeLaser = 0;
    private Infernoid infernoid;
    private long damageImageStartTime = 0;
    private final long damageImageDuration = 100;




    @FXML
    private ImageView infernoidImageView;
    @FXML
    private ImageView playerShip;
    @FXML
    public AnchorPane InfernoidAnchorPane;

    // Flags to track key states
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;
    private boolean shoot = false;

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

    public void main(){

        InfernoidAnchorPane.setFocusTraversable(true);
        InfernoidAnchorPane.requestFocus();
        gameloop = new Timeline(new KeyFrame(Duration.millis(16), event -> {


            elapsedTimeShotable += 7;

            infernoid.move();
            infernoidImageView.setX(infernoid.getCoordX());
            infernoidImageView.setY(infernoid.getCoordY());

            if(playerAttached){
               player.setImageView(playerShip);
            }




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


            try {
                if(player.checkCollision(infernoidImageView,infernoid)){
                    System.out.println("FMCDKMFKL");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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




            if(player.getCoordX() < 0){
                player.setCoordX(0);
                playerShip.setX(0);
            }
            if(player.getCoordY() < 0){
                player.setCoordY(0);
                playerShip.setY(0);
            }
            if(player.getCoordX() > 1450){
                player.setCoordX(1450);
                playerShip.setX(1450);
            }
            if(player.getCoordY() > 750){
                player.setCoordY(750);
                playerShip.setY(750);
            }

            if(infernoidImageView.getX() < -500){
                Infernoid.getInfernoid().setcoordX(-490);
                infernoidImageView.setX(-490);
                Infernoid.getInfernoid().setCollisionLeft(true);
            }
            if(infernoidImageView.getY() < -50){
                Infernoid.getInfernoid().setcoordY(-40);
                infernoidImageView.setY(-40);
                Infernoid.getInfernoid().setCollisionTop(true);
            }
            if(infernoidImageView.getX() > 810){
                Infernoid.getInfernoid().setcoordX(800);
                infernoidImageView.setX(800);
                Infernoid.getInfernoid().setCollisionRight(true);
            }
            if(infernoidImageView.getY() > 600){
                Infernoid.getInfernoid().setcoordY(590);
                infernoidImageView.setY(590);
                Infernoid.getInfernoid().setCollisionDown(true);
            }

        }));

        gameloop.setCycleCount(Timeline.INDEFINITE);
        gameloop.play();
    }


    public static void attachPlayer(Player p){
        player = p;
        playerAttached = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start the game loop for player movement
        infernoid = Infernoid.getInfernoid();
        main();
    }
}
