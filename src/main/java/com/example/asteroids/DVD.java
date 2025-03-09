package com.example.asteroids;

public class DVD extends Asteroid{

    //fields
    private static int healthPoints = 1000;
    private static int speedX = 2;
    private static int speedY = 2;
    private static String imPath = "C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\Dvd.png";


    public DVD() {

        super(healthPoints, speedX, speedY,imPath);
    }//end of Constructor

}//end of DVD
