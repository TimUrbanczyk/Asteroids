package com.example.asteroids.Asteroids;

import com.example.asteroids.Items.BoostItem;
import com.example.asteroids.Items.LaserItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Asteroid {

    private static final String[] spawnPoints = {"NORTH","EAST","SOUTH","WEST"};
    private static ArrayList<Asteroid> asteroids = new ArrayList<>();
    private static boolean spawnable = true;
    private double speedX;
    private double speedY;
    private double coordX;
    private double coordY;
    private final double[] spawnPoint;
    private final String imgPath;
    private final ImageView asteroidImage;
    private final int damagePoints;
    private final long spawnTime = System.currentTimeMillis();
    private String name;



    public Asteroid(double speedX, double speedY, String imgPath, int damagePoints, String name) {

        this.name = name;

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
        }else {
            this.speedX = speedX;
        }

        if(randomY == 0) {
            this.speedY = -speedY;
        }else {
            this.speedY = speedY;
        }



        Image img = new Image(Objects.requireNonNull(getClass().getResource(imgPath)).toExternalForm());
        this.asteroidImage = new ImageView(img);
        this.asteroidImage.setX(spawnPoint[0]);
        this.asteroidImage.setY(spawnPoint[1]);

    }

    public static void spawnAsteroid(){

        if(!spawnable){
            return;
        }
        Random random = new Random();
        double numberRandom = random.nextDouble();

        if(numberRandom < 0.5){
            asteroids.addFirst(new SmallAsteroid());
        }else if(numberRandom < 0.51){
           asteroids.addFirst(new Disruptor());
        }else if(numberRandom < 0.7){
            asteroids.addFirst(new BigAsteroid());
        }else if(numberRandom < 0.75){
            asteroids.addFirst(new HealingAsteroid());
        }else if(numberRandom< 0.8){
            asteroids.addFirst(new LaserItem());
        }else if(numberRandom < 0.83){
            asteroids.addFirst(new BoostItem());
        }else if(numberRandom < 0.84){
            DualityCores newDualityCore = new DualityCores(true);
            newDualityCore.linkToSecondaryCore();
            asteroids.addFirst(newDualityCore);
        }
    }

    public void move(){
        this.setcoordX(this.getCoordX() + this.getSpeedX());
        this.setcoordY(this.getCoordY() + this.getSpeedY());
        this.getAsteroidImage().setX(this.getCoordX());
        this.getAsteroidImage().setY(this.getCoordY());

    }

    public static double[] generateSpawnPoint(){

        double[] Output = new double[2];
        Random random = new Random();
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

    }

    public void despawnAsteroid(){
        if(this instanceof DualityCores){
            DualityCores dualityCore = (DualityCores) this;

            if(dualityCore.isMainCore()){
                DualityCores secondaryCore = dualityCore.getSecondaryCore();
                if(secondaryCore != null){
                    secondaryCore.removeImage();
                    getAsteroids().remove(secondaryCore);
                }
            } else {
                return;
            }
        }
        removeImage();
        getAsteroids().remove(this);
    }

    public boolean checkDespawn(){
        if(System.currentTimeMillis() - this.spawnTime > 10000){
            despawnAsteroid();
            return true;
        }
        return false;
    }

    public static void  despawnAll(){
        asteroids = new ArrayList<>();
    }

    public static void disableSpawn(){
        spawnable = false;
    }

    public void removeImage(){
        this.asteroidImage.setImage(null);
    }

    public String getName(){
        return this.name;
    }

    public int getDamagePoints() {
        return this.damagePoints;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }


    public static ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public double getCoordX() {
        return this.coordX;
    }

    public double getCoordY() {
        return this.coordY;
    }

    public ImageView getAsteroidImage() {
        return this.asteroidImage;
    }

    public void setcoordX(double coordX) {
        this.coordX = coordX;
    }

    public void setcoordY(double coordY) {
        this.coordY = coordY;
    }

    public static void addAsteroid(Asteroid a){
        asteroids.addFirst(a);
    }

    public static void setAsteroids(ArrayList<Asteroid> a) {
        asteroids = a;
    }

    public void setSpeedX(double speedX){
        this.speedX = speedX;
    }
    public void setSpeedY(double speedY){
        this.speedY = speedY;
    }


}

