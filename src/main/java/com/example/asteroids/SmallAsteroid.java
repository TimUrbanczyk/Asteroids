package com.example.asteroids;

public class SmallAsteroid extends Asteroid {

    //static fields
    private static int healthPoints = 20;
    private static int speedX = 2;
    private static int speedY = 2;
    private static String imPath = "C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\SmallAsteroid.png";


    public SmallAsteroid() {
        super(healthPoints,speedX,speedY,imPath);
        super.getAsteroidImage().setScaleX(0.8);
        super.getAsteroidImage().setScaleY(0.8);
    }//end of constructor

}//end of SmallAsteroid
