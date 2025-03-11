package com.example.asteroids;

import java.util.Random;

public class SmallAsteroid extends Asteroid {

    //static fields
    private static int healthPoints = 30;
    private static double speedX = 4;
    private static double speedY  = -4;
    private static int damagePoints = 30;
    private static String imPath = "C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\SmallAsteroid.png";

    public SmallAsteroid() {

        super(healthPoints,speedX,speedY,imPath,damagePoints);
        super.getAsteroidImage().setScaleX(0.8);
        super.getAsteroidImage().setScaleY(0.8);

        Random random = new Random();

        speedX = random.nextDouble(3.5)+1;
        speedY = random.nextDouble(3.5)+1;

    }//end of constructor

}//end of SmallAsteroid
