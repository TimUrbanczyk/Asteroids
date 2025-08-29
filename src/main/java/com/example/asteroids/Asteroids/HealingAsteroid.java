package com.example.asteroids.Asteroids;


public class HealingAsteroid extends Asteroid {


    private static final double speedX =5 ;
    private static final double speedY = 5;
    private static final int damagePoints = -50;
    private static final String imgPath = "/imgs/HealAsteroid.png";
    private static final String name = "ShootingStar";


    public HealingAsteroid() {
        super(speedX,speedY,imgPath,damagePoints,name);
        super.getAsteroidImage().setScaleX(0.15);
        super.getAsteroidImage().setScaleY(0.15);
    }


}
