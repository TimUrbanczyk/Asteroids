package com.example.asteroids.Asteroids;

public class Disruptor extends Asteroid{

    private static double speedX = 4;
    private static double speedY = 4;
    private static final int damagePoints = 30;
    private static final String imgPath = "/imgs/Disruptor.png";
    private static final String name = "Disruptor";

    public Disruptor(){
        super(speedX,speedY,imgPath,damagePoints,name);

        super.getAsteroidImage().setFitWidth(60);
        super.getAsteroidImage().setFitHeight(60);
    }
}
