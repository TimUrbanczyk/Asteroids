package com.example.asteroids.Weapons;


import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Player;

import java.util.Random;

/*
 * I want only one laser at the same time
 * therefore i use singleton pattern here
 */
public class Laser {

    //fields
    private double coordX;
    private double coordY;
    private double length = 1000;
    private double with = 75;
    private double angle;
    private Player player;
    private boolean shootableFlag = false;
    private long shootableInterval = 5000;
    private long spawnTime = System.currentTimeMillis();
    private static Laser instance;


    private Laser(){

        this.coordX = player.getCoordX();
        this.coordY = player.getCoordY();

        Random rand = new Random();
        int randomNum = rand.nextInt(360);
        this.angle = randomNum;


    }//end of constructor


    public static Laser getInstance(){
        if (instance == null) {
            instance = new Laser();
        }
        return instance;
    }//end of getInstance

    public void attachPlayer(Player player){this.player = player;}


    public boolean checkColision(){
        for(Asteroid asteroid : Asteroid.getAsteroids()){
            if(asteroid.getAsteroidImage().getBoundsInParent().intersects(this.coordX,this.coordY,this.length,this.with)){
                asteroid.despawnAsteroid();
                return true;
            }
        }
        return false;

    }//end of checkColission


    public double getCoordX() {return this.coordX;}
    public double getCoordY() {return this.coordY;}
    public double getLength() {return this.length;}
    public double getWith() {return this.with;}
    public double getAngle() {return this.angle;}
    public boolean isShootable(){return this.shootableFlag;}
    public long getShootableInterval(){return this.shootableInterval;}




}//end of Laser
