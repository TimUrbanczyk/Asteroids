package com.example.asteroids.Asteroids;

public class Infernoid extends Asteroid {

    private static final double speedX = 6;
    private static final double speedY = 6;
    private static final int damagePoints = 100;
    private static final String imgPath = "/imgs/Infernoid.png";
    private static final String name = "Infernoid";

    public Infernoid(){
        super(speedX,speedY,imgPath,damagePoints,name);
        super.getAsteroidImage().setFitWidth(60);
        super.getAsteroidImage().setFitHeight(60);
    }









}
