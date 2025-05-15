package com.example.asteroids.Asteroids;

import com.example.asteroids.Items.LaserItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Random;

public class Asteroid {

    //static fields
    private static String[] spawnPoints = {"NORTH","EAST","SOUTH","WEST"}; //coords of possible spawn points (x,y)
    private static ArrayList<Asteroid> asteroids = new ArrayList<>();

    //fields
    private double speedX;
    private double speedY;
    private double coordX;
    private double coordY;
    private double[] spawnPoint;
    private String imgPath;
    private ImageView asteroidImage;
    private boolean wasOnScreen;
    private int damagePoints;
    private long spawnTime = System.currentTimeMillis();

    public Asteroid(double speedX, double speedY, String imgPath, int damagePoints) {


        spawnPoint = generateSpawnPoint();
        coordX = spawnPoint[0];
        coordY = spawnPoint[1];

        this.imgPath = imgPath;
        this.damagePoints = damagePoints;

        Random rand = new Random();
        int randomX = rand.nextInt(2);
        int randomY = rand.nextInt(2);

        if(randomX == 0) {
            this.speedX = -speedX;
        }else if(randomX == 1) {
            this.speedX = speedX;
        }

        if(randomY == 0) {
            this.speedY = -speedY;
        }else if(randomY == 1) {
            this.speedY = speedY;
        }



        Image img = new Image(getClass().getResource(imgPath).toExternalForm());
        this.asteroidImage = new ImageView(img);

        this.asteroidImage.setX(spawnPoint[0]);
        this.asteroidImage.setY(spawnPoint[1]);



    }//end of constuctor

    public static void spawnAsteroid(){

        Random random = new Random(); //set up random
        double numberRandom = random.nextDouble();

        //comment the next line in for debugging purpose
        //System.out.println("[ASTEROID SPAWNED] TOTAL NUMBER OF ASTEROIDS: " + asteroids.size());


        if(numberRandom < 0.8){
            asteroids.addFirst(new SmallAsteroid());
        }else if(numberRandom < 0.85 && numberRandom >= 0.8){
            asteroids.addFirst(new BigAsteroid());
        }else if(numberRandom >= 0.85 && numberRandom < 0.9){
            asteroids.addFirst(new HealingAsteroid());
        }else if(numberRandom >= 0.9){
            asteroids.addFirst(new LaserItem());
        }


    }//end of spawnAsteroid



    public static void moveAsteroid(){
        for(Asteroid asteroid : asteroids){

            asteroid.setcoordX(asteroid.getCoordX()+asteroid.getSpeedX());
            asteroid.setcoordY(asteroid.getCoordY()+asteroid.getSpeedY());

            asteroid.getAsteroidImage().setX(asteroid.getCoordX());
            asteroid.getAsteroidImage().setY(asteroid.getCoordY());
        }
    }//end of moveAsteroids

    public static double[] generateSpawnPoint(){

        double[] Output = new double[2];
        Random random = new Random(); //set up random
        int numberRandom = random.nextInt(spawnPoints.length);


        switch(spawnPoints[numberRandom]){
            case "NORTH":
                Output[0] = random.nextDouble(750);
                Output[1] = -100;

                break;
            case "EAST":
                Output[0] = 1600;
                Output[1] = random.nextDouble(760);

                break;
            case "SOUTH":
                Output[0] = random.nextDouble(750);
                Output[1] = 900;
                break;
            case "WEST":
                Output[0] = -150;
                Output[1] = random.nextDouble(760);
                break;
            default:
                break;
        }

        return Output;

    }//end of generateSpawnPoint

    public void despawnAsteroid(){
        removeImage();
        getAsteroids().remove(this);

    }


    public boolean checkDespawn(){
        if(System.currentTimeMillis() - this.spawnTime > 10000){
            despawnAsteroid();
            return true;
        }
        return false;
    }//end of checkDespawn



    public void removeImage(){
        this.asteroidImage.setImage(null);
    }

    //getters
    public int getDamagePoints() {return this.damagePoints;}

    public double getSpeedX() {return this.speedX;}

    public double getSpeedY() {return this.speedY;}


    public static ArrayList<Asteroid> getAsteroids() {return asteroids;}

    public double getCoordX() {return this.coordX;}

    public double getCoordY() {return this.coordY;}

    public ImageView getAsteroidImage() {return this.asteroidImage;}


    //setters

    public void setcoordX(double coordX) {this.coordX = coordX;}

    public void setcoordY(double coordY) {this.coordY = coordY;}

    public void setWasOnScreen(boolean wasOnScreen) {this.wasOnScreen = wasOnScreen;}

    public static void setAsteroids(ArrayList<Asteroid> a) {asteroids = a;}

}//end of Asteroid

