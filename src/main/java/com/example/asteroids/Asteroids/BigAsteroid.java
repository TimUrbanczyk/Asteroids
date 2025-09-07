package com.example.asteroids.Asteroids;

import java.util.Random;

public class BigAsteroid extends Asteroid {

    private static double speedX =2.7 ;
    private static double speedY = 2.7;
    private static final int damagePoints = 70;
    private static final String imgPath = "/imgs/BigAstroid.png";
    private static final String name = "BigAsteroid";

    public BigAsteroid(){
        super(speedX,speedY,imgPath,damagePoints,name);
        super.getAsteroidImage().setScaleX(0.3);
        super.getAsteroidImage().setScaleY(0.3);
        Random random = new Random();
        speedX = random.nextDouble(2.3)+1;
        speedY = random.nextDouble(2.3)+1;
    }

}
