package com.example.asteroids;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class Player {

    private int healthPoints;
    private ImageView imageView;
    private double coordX;
    private double coordY;
    private final double width;
    private final double height;
    private double speed = 6.5;
    private double rotationSpeed = 4.5;
    private final long getInvincibleAfterTakingDamageInterval = 1500;
    private long elapsedTime = 0;

    public Player( int healthPoints, ImageView imageView) {

        this.healthPoints = healthPoints;
        this.imageView = imageView;
        this.coordX = imageView.getX();
        System.out.println(imageView);
        System.out.println(coordX);
        this.coordY = imageView.getY();
        this.width = imageView.getBoundsInParent().getWidth()-20;
        this.height = imageView.getBoundsInParent().getHeight()-10;

    }//end of constructor

    public boolean checkCollision( ImageView target) {

        for(Asteroid asteroid : Asteroid.getAsteroids()) {

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


    }//end of handleCollision


    private void calculateNewHealthPoints(Asteroid asteroid) {
        if(elapsedTime > getInvincibleAfterTakingDamageInterval) {
            this.healthPoints = this.healthPoints - asteroid.getHealthPoints();
            System.out.println(this.healthPoints);
            elapsedTime = 0;
            checkForDeath();
        }elapsedTime+=1;

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


    //setters

    public void setCoordX(double coordX) {this.coordX = coordX;}

    public void setCoordY(double coordY) {this.coordY = coordY;}
}//end of Player
