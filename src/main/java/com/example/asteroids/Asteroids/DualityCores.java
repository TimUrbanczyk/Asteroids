package com.example.asteroids.Asteroids;

import javafx.scene.image.Image;

import java.util.Random;

public class DualityCores extends Asteroid{

    private static final double speedX =2.7 ;
    private static final double speedY = 2.7;
    private static final int damagePoints = 100;
    private static final String imgPathMainCore = "/imgs/DualityMainCore.png";
    private static final String imgPathSecondaryCore = "/imgs/DualitySecondaryCore.png";
    private static String currentImgPath = imgPathMainCore;
    private static final String name = "DualityCores";
    private final boolean isMainCore;
    private DualityCores secondaryCore;

    public DualityCores(boolean isMainCore){
        super(speedX, speedY, imgPathMainCore, damagePoints, name);
        this.isMainCore = isMainCore;
        super.getAsteroidImage().setFitWidth(50);
        super.getAsteroidImage().setFitHeight(50);

        if(!this.isMainCore){
            Image imageSecondaryCore = new Image(getClass().getResource(imgPathSecondaryCore).toExternalForm());
            super.getAsteroidImage().setImage(imageSecondaryCore);
        }
    }


    public void linkToSecondaryCore() {
        if (!this.isMainCore) {
            throw new RuntimeException("Only Main Cores can be linked to others!");
        }
        Random rand = new Random();
        double offsetX = rand.nextDouble(120)+150;
        double offsetY = rand.nextDouble(120)+150;
        this.secondaryCore = new DualityCores(false);
        this.secondaryCore.setcoordX(this.getCoordX() + offsetX);
        this.secondaryCore.setcoordY(this.getCoordY() + offsetY);
        this.secondaryCore.getAsteroidImage().setX(this.getCoordX() + offsetX);
        this.secondaryCore.getAsteroidImage().setY(this.getCoordY() + offsetY);
        this.secondaryCore.setSpeedX(this.getSpeedX());
        this.secondaryCore.setSpeedY(this.getSpeedY());
        addAsteroid(this.secondaryCore);
    }



    public double[] getTetherCoords(){
        double coordXMainCore = this.getAsteroidImage().getX();
        double coordYMainCore = this.getAsteroidImage().getY();
        double coordXSecondaryCore = secondaryCore.getAsteroidImage().getX();
        double coordYSecondaryCore = secondaryCore.getAsteroidImage().getY();
        return new double[]{coordXMainCore,coordYMainCore,coordXSecondaryCore,coordYSecondaryCore};
    }


    public boolean isMainCore(){
        return this.isMainCore;
    }

    public DualityCores getSecondaryCore(){
        return this.secondaryCore;
    }


}
