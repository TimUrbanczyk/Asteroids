package com.example.asteroids.Asteroids;

import java.util.Random;

public class SmallAsteroid extends Asteroid {

    private static double speed = 4;
    private static final int damagePoints = 30;
    private static final String imPath = "/imgs/SmallAsteroid.png";
    private static final String name = "SmallAsteroid";

    public SmallAsteroid() {
        super(speed,imPath,damagePoints,name);
        super.getAsteroidImage().setScaleX(0.6);
        super.getAsteroidImage().setScaleY(0.6);

        Random random = new Random();
        speed = random.nextDouble(3.5)+1;
    }

}
