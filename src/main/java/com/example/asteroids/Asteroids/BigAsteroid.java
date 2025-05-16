package com.example.asteroids.Asteroids;

import java.util.Random;

public class BigAsteroid extends Asteroid {

    //static fields
    private static double speedX =2.7 ;
    private static double speedY = 2.7;
    private static int damagePoints = 100;
    private static String imgPath = "/imgs/BigAstroid.png";
    private static String name = "BigAsteroid";

    public BigAsteroid(){

        super(speedX,speedY,imgPath,damagePoints);
        super.getAsteroidImage().setScaleX(0.4);
        super.getAsteroidImage().setScaleY(0.4);

        Random random = new Random();

        speedX = random.nextDouble(2.3)+1;
        speedY = random.nextDouble(2.3)+1;


    }//end of constructor

    public static String getName(){return name;}

}//end of BigAsteroid
