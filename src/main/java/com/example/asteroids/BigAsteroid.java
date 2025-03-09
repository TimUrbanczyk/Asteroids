package com.example.asteroids;

public class BigAsteroid extends Asteroid {

    //static fields
    private static int healthPoints = 40;
    private static int speedX = 1;
    private static int speedY = 1;
    private static String imgPath = "C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\BigAstroid.png";

    public BigAsteroid(){
        super(healthPoints,speedX,speedY,imgPath);
        super.getAsteroidImage().setScaleX(0.4);
        super.getAsteroidImage().setScaleY(0.4);
    }//end of constructor

}//end of BigAsteroid
