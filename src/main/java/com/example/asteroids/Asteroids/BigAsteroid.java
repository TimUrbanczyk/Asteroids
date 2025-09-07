package com.example.asteroids.Asteroids;

import java.util.Random;

public class BigAsteroid extends Asteroid {

    private static double speed =2.7 ;;
    private static final int damagePoints = 70;
    private static final String imgPath = "/imgs/BigAstroid.png";
    private static final String name = "BigAsteroid";

    public BigAsteroid(){
        super(speed,imgPath,damagePoints,name);
        super.getAsteroidImage().setScaleX(0.3);
        super.getAsteroidImage().setScaleY(0.3);
        Random random = new Random();
        speed = random.nextDouble(2.3)+1;
    }

}
