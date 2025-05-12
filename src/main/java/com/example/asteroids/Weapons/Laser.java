package com.example.asteroids.Weapons;


import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.PlayerPackage.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/*
 * I want only one laser at the same time
 * therefore i use singleton pattern here
 */
public class Laser {

    //fields
    private double length = 1000;
    private double width = 3;
    private double angle;
    private Player player;
    private boolean shootableFlag = false;
    private long shootableInterval = 500;
    private long spawnTime = System.currentTimeMillis();
    private static Laser instance;
    private final Color laserColor = Color.RED;


    private Laser(){


        Random rand = new Random();
        int randomNum = rand.nextInt(360);
        this.angle = randomNum;


    }//end of constructor


    private void randomizeAngle(){
        Random rand = new Random();
        int randomNum = rand.nextInt(360);
        this.angle = randomNum;
    }


    public static Laser getInstance(){
        if (instance == null) {
            instance = new Laser();
        }
        return instance;
    }//end of getInstance

    public void attachPlayer(Player player){this.player = player;}

    public void shoot(){
        randomizeAngle();
        checkColision();
    }


    private void checkColision(){
        for(Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())){
           double x1 = asteroid.getAsteroidImage().getBoundsInParent().getMinX();
           double y1 = asteroid.getAsteroidImage().getBoundsInParent().getMinY();
           double x2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxX();
           double y2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxY();
           double x3 = this.player.getCoordX()+this.player.getWidth()/2-100;
           double y3 = this.player.getCoordY()+this.player.getHeight()/2;
           double x4 = x3+this.length*Math.cos(Math.toRadians(this.angle));
           double y4 = y3+this.length*Math.sin(Math.toRadians(this.angle));


           double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

           double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

           if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
               asteroid.despawnAsteroid();
               player.setPoints(player.getPoints()+1);
           }

           x1 = asteroid.getAsteroidImage().getBoundsInParent().getMinX();
           y1 = asteroid.getAsteroidImage().getBoundsInParent().getMinY();
           x2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxX();
           y2 = asteroid.getAsteroidImage().getBoundsInParent().getMinY();

           uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

           uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

           if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
               asteroid.despawnAsteroid();
               player.setPoints(player.getPoints()+1);
           }


            x1 = asteroid.getAsteroidImage().getBoundsInParent().getMaxX();
            y1 = asteroid.getAsteroidImage().getBoundsInParent().getMinY();
            x2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxX();
            y2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxY();

            uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

            uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

            if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
                asteroid.despawnAsteroid();
                player.setPoints(player.getPoints()+1);
            }


            x1 = asteroid.getAsteroidImage().getBoundsInParent().getMinX();
            y1 = asteroid.getAsteroidImage().getBoundsInParent().getMaxY();
            x2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxX();
            y2 = asteroid.getAsteroidImage().getBoundsInParent().getMaxY();

            uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

            uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

            if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
                asteroid.despawnAsteroid();
                player.setPoints(player.getPoints()+1);
            }

        }


    }//end of checkColission



    public double getLength() {return this.length;}
    public double getWidth() {return this.width;}
    public double getAngle() {return this.angle;}
    public Color getLaserColor() {return this.laserColor;}
    public long getShootableInterval(){return this.shootableInterval;}
    public boolean isShootable(){return this.shootableFlag;}


    public void setShootable(boolean s){this.shootableFlag = s;}
    public void increaseWidth(){this.width += 1;}
    public void resetLaser(){
        this.shootableFlag = false;
        this.width = 3;
    }


}//end of Laser
