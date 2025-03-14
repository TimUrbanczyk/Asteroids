package com.example.asteroids;


//DVD can heal the player by hitting it. Now it is about 50 HP. I kind of think about over healing. Should it give extra HP???
public class DVD extends Asteroid{

    //fields
    private static int healthPoints = 1000;
    private static double speedX = 2;
    private static double speedY = 2;
    private static int damagePoints = -50;
    private static String imPath = "C:\\Users\\TimUr\\IdeaProjects\\study\\Asteroids\\src\\main\\resources\\imgs\\Dvd.png";


    public DVD() {

        super(speedX, speedY,imPath,damagePoints);
    }//end of Constructor

}//end of DVD
