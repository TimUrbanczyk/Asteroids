package com.example.asteroids.Asteroids;

import java.util.Random;

public class SmallAsteroid extends Asteroid {

    private static double speedX =4;
    private static double speedY =4;
    private static final int damagePoints = 30;
    private static final String imPath = "/imgs/SmallAsteroid.png";
    private static final String name = "SmallAsteroid";

    public SmallAsteroid() {
        super(speedX,speedY,imPath,damagePoints,name);
        super.getAsteroidImage().setFitHeight(50);
        super.getAsteroidImage().setFitWidth(50);

        Random random = new Random();
        speedX = random.nextDouble(3.5)+1;
        speedY = random.nextDouble(3.5)+1;
    }

}
