package com.example.asteroids;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Stack;

public class Player {

    private int healthPoints;
    private ImageView imageView;
    private double coordX;
    private double coordY;
    private final double width;
    private final double height;
    private double speed = 6.5;
    private double rotationSpeed = 4.5;
    private Stack<Bullet> bullets = new Stack<>(); //use a stack to make it easier to despawn the bullets after a certian time
    private Healthbar healthBar ;

    /*
    I am planning to have about 10-15 bullets active at the same time.
     */

    public Player(int healthPoints, ImageView imageView) {

        this.healthBar = healthBar;
        this.healthPoints = healthPoints;
        this.imageView = imageView;
        this.coordX = imageView.getX();
        this.coordY = imageView.getY();
        this.width = imageView.getBoundsInParent().getWidth()-20;
        this.height = imageView.getBoundsInParent().getHeight()-10;

    }//end of constructor

    public boolean checkCollision(ImageView target) {

        for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())) {

            //set up the both bounds as good as possible somehow i wasted to much time here idk
            Bounds asteroidBounds = new BoundingBox(
                    asteroid.getAsteroidImage().getBoundsInParent().getMinX(),
                    asteroid.getAsteroidImage().getBoundsInParent().getMinY(),
                    asteroid.getAsteroidImage().getBoundsInParent().getWidth(),
                    asteroid.getAsteroidImage().getBoundsInParent().getHeight()
            );

            Bounds playerBounds = new BoundingBox(
                    this.getImageView().getBoundsInParent().getMinX(),
                    this.getImageView().getBoundsInParent().getMinY(),
                    this.width,
                    this.height

            );

            if(playerBounds.intersects(asteroidBounds)) {

                //comment in the line for debugging purpose it shows the collisions in the console with timestamp
                //System.out.println("Collision"+System.currentTimeMillis());

                calculateNewHealthPoints(asteroid);
                handleCollision(asteroid);
                return true;

            }
        }

        return false;


    }//end of checkCollision

    public void handleCollision( Asteroid asteroid) {

            asteroid.despawnAsteroid();

    }//end of handleCollision

    private void calculateNewHealthPoints(Asteroid asteroid) {
            this.healthPoints = this.healthPoints - asteroid.getHealthPoints();
            System.out.println(this.healthPoints);
            checkForDeath();


    }//end of calculateNewHealthPoints

    private void checkForDeath() {
        if (this.healthPoints <= 0){
            GUI.setGameRunning(false);
        }
    }//end of checkForDeath


    //getters
    public int getHealthPoints() {return healthPoints;}

    public ImageView getImageView() {return imageView;}

    public double getSpeed() {return speed;}

    public double getRotationSpeed() {return rotationSpeed;}

    public double getCoordX() {return coordX;}

    public double getCoordY() {return coordY;}

    public double getWidth() {return width;}

    public double getHeight() {return height;}

    public Stack<Bullet> getBullets() {return bullets;}

    public Healthbar getHealthBar() {return healthBar;}


    //setters

    public void setCoordX(double coordX) {this.coordX = coordX;}

    public void setCoordY(double coordY) {this.coordY = coordY;}

    public void attachHealthBar(Healthbar healthBar) {this.healthBar = healthBar;}
}//end of Player
