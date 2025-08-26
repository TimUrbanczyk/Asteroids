package com.example.asteroids.BossFights.Bosses;


import com.example.asteroids.Asteroids.Asteroid;

import java.util.Random;

//eed one instance for the boss, therefore use singletoen here
public class Infernoid extends Asteroid {


    //singleton instance
    private static Infernoid infernoid;
    //fields
    private static final double speedX =2.7 ;
    private static final double speedY = 2.7;
    private static final int damagePoints = 100;
    private static final String name = "Infernoid";


    private Infernoid(){
        super(speedX,speedY,"",damagePoints);
    }

    public static Infernoid getInfernoid(){
        if(infernoid == null){
            infernoid = new Infernoid();
        }

        return infernoid;
    }


    public double[] calculateNewCoords(double oldCoordX, double oldCoordY){


        Random rand = new Random();

        double newCoordX = rand.nextDouble()+1;
        double newCoordY = rand.nextDouble()+1;

        double[] output = new double[2];
        output[0] = newCoordX;
        output[1] = newCoordY;
        return output;


    }


}
