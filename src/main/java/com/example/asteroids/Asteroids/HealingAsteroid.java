package com.example.asteroids.Asteroids;

/*
The idea is to make shooting star/star that spawns with a percentage of 0.01
This shooting star should heal the player on collision
The player is allowed to kill the shooting star
 */

public class HealingAsteroid extends Asteroid {

    //static fields
    private static double speedX =5 ;
    private static double speedY = 5;
    private static int damagePoints = -50; // negative damage == healing (5head)
    private static String imgPath = "/imgs/HealAsteroid.png";
    private static String name = "ShootingStar";


    public HealingAsteroid() {
        super(speedX,speedY,imgPath,damagePoints);
        super.getAsteroidImage().setScaleX(0.15);
        super.getAsteroidImage().setScaleY(0.15);
    }//end of constructor

    public static String getName(){return name;}

}//end of HealingAsteroid
