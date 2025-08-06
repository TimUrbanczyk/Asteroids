package com.example.asteroids.Items;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Weapons.Laser;


//extend Asteroid because i want to make the items to fly around
public class LaserItem extends Asteroid implements ItemInterface{

    //static fields
    private static double speedX = 2.3 ;
    private static double speedY = 2.3;
    private static int damagePoints = 0;
    private static String imgPath = "/imgs/LaserItem.png";
    private static String name = "LaserUpgread";


    public LaserItem(){
        super(speedX,speedY,imgPath,damagePoints);
        super.getAsteroidImage().setScaleX(0.15);
        super.getAsteroidImage().setScaleY(0.15);
    }


    @Override
    public void onCollision(){
        if(!Laser.getInstance().isShootable()){
            Laser.getInstance().setShootable(true);
        }else{
            Laser.getInstance().increaseWidth();
        }
    }

    public static String getName(){return name;}
}
