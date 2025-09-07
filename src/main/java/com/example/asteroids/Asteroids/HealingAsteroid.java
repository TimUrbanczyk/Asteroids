package com.example.asteroids.Asteroids;


public class HealingAsteroid extends Asteroid {


    private static final double speed =5 ;
    private static final int damagePoints = -50;
    private static final String imgPath = "/imgs/HealAsteroid.png";
    private static final String name = "ShootingStar";


    public HealingAsteroid() {
        super(speed,imgPath,damagePoints,name);
        super.getAsteroidImage().setScaleX(0.1);
        super.getAsteroidImage().setScaleY(0.1);
    }


}
