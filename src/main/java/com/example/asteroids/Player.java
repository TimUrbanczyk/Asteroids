package com.example.asteroids;

import javafx.scene.image.ImageView;

public class Player {


    private int healthPoints;
    private ImageView imageView;
    private double speed = 6.5;
    private double rotationSpeed = 4.5;

    public Player( int healthPoints, ImageView imageView) {


        this.healthPoints = healthPoints;
        this.imageView = imageView;

    }//end of constructor

    //getters

    public int getHealthPoints() {return healthPoints;}

    public ImageView getImageView() {return imageView;}

    public double getSpeed() {return speed;}

    public double getRotationSpeed() {return rotationSpeed;}
}//end of Player
