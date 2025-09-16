package com.example.asteroids.Player;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Asteroids.Disruptor;
import com.example.asteroids.Asteroids.DualityCores;
import com.example.asteroids.Asteroids.HealingAsteroid;
import com.example.asteroids.Items.ItemInterface;
import com.example.asteroids.Journal.DiscoveredEntities;
import com.example.asteroids.Weapons.Bullet;
import com.example.asteroids.Level.MainWindowController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

public class Player {

    private int healthPoints;
    private ImageView imageView;
    private double coordX;
    private double coordY;
    private final double width;
    private final double height;
    private final double speed = 11;
    private final double rotationSpeed = 6;
    private Stack<Bullet> bullets = new Stack<>();
    private Healthbar healthBar ;
    private static final String name = "Spaceship";
    private final Image damageImage = new Image(getClass().getResource("/imgs/SpaceShipPlayerOnDamage.png").toExternalForm());
    private Image playerImage;
    private boolean isInDamageState = false;
    private boolean stunned = false;


    public Player(int healthPoints, ImageView imageView) {

        this.healthBar = healthBar;
        this.playerImage = imageView.getImage();
        this.healthPoints = healthPoints;
        this.imageView = imageView;
        this.coordX = imageView.getX();
        this.coordY = imageView.getY();
        this.imageView.setScaleX(0.6);
        this.imageView.setScaleY(0.6);
        this.width = imageView.getBoundsInParent().getWidth()-20;
        this.height = imageView.getBoundsInParent().getHeight()-10;

    }

    public boolean checkCollision(ImageView targetImageView, Asteroid target) throws IOException {


        Bounds asteroidBounds = new BoundingBox(
                    targetImageView.getBoundsInParent().getMinX(),
                    targetImageView.getBoundsInParent().getMinY(),
                    targetImageView.getBoundsInParent().getWidth(),
                    targetImageView.getBoundsInParent().getHeight()
            );

        if(target instanceof Disruptor){
            asteroidBounds = new BoundingBox(
                    targetImageView.getBoundsInParent().getMinX() - 15,
                    targetImageView.getBoundsInParent().getMinY() - 15,
                    targetImageView.getBoundsInParent().getWidth() + 30,
                    targetImageView.getBoundsInParent().getHeight() + 30
            );
        }
        Bounds playerBounds = new BoundingBox(
                    this.getImageView().getBoundsInParent().getMinX(),
                    this.getImageView().getBoundsInParent().getMinY(),
                    this.width,
                    this.height

            );

        if(target instanceof DualityCores && ((DualityCores) target).isMainCore()) {
            if(checkCollisionWithDualityCoresTether(playerBounds, (DualityCores) target )) {
                handleCollision(target);
                return true;
            }
        }



        if(playerBounds.intersects(asteroidBounds)) {
                if(!(target instanceof ItemInterface) && !(target instanceof HealingAsteroid)) {
                    this.imageView.setImage(damageImage);
                    this.isInDamageState = true;
                }
                calculateNewHealthPoints(target);
                if(!(target instanceof DualityCores)){
                    handleCollision(target);
                }
                return true;
            }
        return false;

    }

    private boolean checkCollisionWithDualityCoresTether(Bounds playerBounds, DualityCores dualityCores){
        int width = (int) (playerBounds.getMaxX() - playerBounds.getMinX());
        int height = (int) (playerBounds.getMaxY() - playerBounds.getMinY());

        double shrinkFactor = 0.2;
        int newWidth = (int) (width * (1 - shrinkFactor));
        int newHeight = (int) (height * (1 - shrinkFactor));

        int deltaX = (width - newWidth) / 2;
        int deltaY = (height - newHeight) / 2;

        int newX = (int) playerBounds.getMinX() + deltaX;
        int newY = (int) playerBounds.getMinY() + deltaY;

        Rectangle playerBoundsAsRectangle = new Rectangle(newX, newY, newWidth, newHeight);

        double[] dualityCoresTetherCoordinates = dualityCores.getTetherCoords();
        Line2D dualityCoresTetherAsLine = new Line2D.Float((float) dualityCoresTetherCoordinates[0],
                (float) dualityCoresTetherCoordinates[1],
                (float) dualityCoresTetherCoordinates[2],
                (float) dualityCoresTetherCoordinates[3]);

        return dualityCoresTetherAsLine.intersects(playerBoundsAsRectangle);
    }

    public void resetDamageImage(){
        this.imageView.setImage(playerImage);
        this.isInDamageState = false;
    }

    public void handleCollision(Asteroid asteroid) throws IOException {
        addAsteroidToDiscoveredEntities(asteroid);
        asteroid.onCollision();
        asteroid.despawnAsteroid();
    }


    public static void addAsteroidToDiscoveredEntities(Asteroid asteroid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String discoveredEntitiespath = "src/main/java/com/example/asteroids/Journal/DiscoveredEntities.json";
        File discoveredEntitiesFile = new File(discoveredEntitiespath);
        DiscoveredEntities entities = objectMapper.readValue(discoveredEntitiesFile, DiscoveredEntities.class);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(discoveredEntitiesFile, entities);
        String discoveredEntitiesFileAsString = Files.readString(Path.of(discoveredEntitiespath));
        if(discoveredEntitiesFileAsString.contains(asteroid.getName())){
            return;
        }
        entities.getDiscovered().add(asteroid.getName());
        System.out.println(asteroid.getName());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(discoveredEntitiesFile, entities);
    }

    private void calculateNewHealthPoints(Asteroid asteroid) {
            this.healthPoints = this.healthPoints - asteroid.getDamagePoints();
            checkForDeath();
    }

    private void checkForDeath() {
        if (this.healthPoints <= 0){
            MainWindowController.setGameRunning(false);
        }
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Stack<Bullet> getBullets() {
        return bullets;
    }

    public Healthbar getHealthBar() {
        return healthBar;
    }

    public static String getName(){
        return name;
    }

    public boolean getDamageState(){
        return this.isInDamageState;
    }

    public boolean isStunned(){
        return this.stunned;
    }


    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public void attachHealthBar(Healthbar healthBar) {
        this.healthBar = healthBar;
    }

    public void setBullets(Stack<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public void setStunned(boolean stunned){
        this.stunned = stunned;
    }

    public void setHealthPoints(int h) {
        this.healthPoints = h;
    }

}//end of Player
