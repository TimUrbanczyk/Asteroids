package com.example.asteroids;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Asteroid {

    //static fields
    private static double[][] spawnPoints = {{10,-10},{300,-10},{700,-10},{1410,10},{1410,400},{1410,700}}; //coords of possible spawnpoints (x,y)
    private static ArrayList<Asteroid> asteroids = new ArrayList<>();

    //fields
    private int healthPoints;
    private int speedX;
    private int speedY;
    private double coordX;
    private double coordY;
    private double[] spawnPoint;
    private String imgPath;
    private ImageView asteroidImage;


    public Asteroid(int healthPoints, int speedX, int speedY, String imgPath) {


        spawnPoint = generateSpawnPoint();
        coordX = spawnPoint[0];
        coordY = spawnPoint[1];

        this.imgPath = imgPath;
        this.healthPoints = healthPoints;
        this.speedX = speedX;
        this.speedY = speedY;

        this.asteroidImage = new ImageView(new Image(this.imgPath));

        this.asteroidImage.setX(spawnPoint[0]);
        this.asteroidImage.setY(spawnPoint[1]);



    }//end of constuctor



    public static void spawnAsteroid(){

        Random random = new Random(); //set up random
        double numberRandom = random.nextDouble();

        System.out.println("[ASTEROID SPAWNED] TOTAL NUMBER OF ASTEROIDS: " + asteroids.size());




        if(numberRandom < 0.9){
            asteroids.addFirst(new SmallAsteroid());
        }else if(numberRandom < 1 && numberRandom >= 0.9){
            asteroids.addFirst(new BigAsteroid());
        }else if(numberRandom >= 1){
            asteroids.addFirst(new DVD());
        }


    }//end of spawnAsteroid

    public static void moveAsteroid(){
        for(Asteroid asteroid : asteroids){
            asteroid.setcoordX(asteroid.getCoordX()+asteroid.getSpeedX());
            asteroid.setcoordY(asteroid.getCoordY()+asteroid.getSpeedY());
        }
    }//end of moveAsteroids

    public static double[] generateSpawnPoint(){

        Random random = new Random(); //set up random
        int numberRandom = random.nextInt(spawnPoints.length);
        System.out.println(numberRandom);

        return spawnPoints[numberRandom];

    }//end of generateSpawnPoint

    //getters
    public int getHealthPoints() {return this.healthPoints;}

    public int getSpeedX() {return this.speedX;}

    public int getSpeedY() {return this.speedY;}

    public double[] getSpawnPoint() {return this.spawnPoint;}

    public static ArrayList<Asteroid> getAsteroids() {return asteroids;}

    public double getCoordX() {return this.coordX;}

    public double getCoordY() {return this.coordY;}

    public String getImgPath() {return this.imgPath;}

    public ImageView getAsteroidImage() {return this.asteroidImage;}

    //setters
    public void setHealthPoints(int healthPoints) {this.healthPoints = healthPoints;}

    public void setSpeedX(int speedX) {this.speedX = speedX;}

    public void setSpeedY(int speedY) {this.speedY = speedY;}

    public void setcoordX(double coordX) {this.coordX = coordX;}

    public void setcoordY(double coordY) {this.coordY = coordY;}








}//end of Asteroid

