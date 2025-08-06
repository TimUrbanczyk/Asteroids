package com.example.asteroids.Items;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Weapons.Bullet;

public class BoostItem extends Asteroid implements ItemInterface{

    //static fields
    private static double speedX = 3.5 ;
    private static double speedY = 3.5;
    private static int damagePoints = 0;
    private static String imgPath = "/imgs/BoostItem.png";
    private static String name = "BoostUpgread";


    public BoostItem(){
        super(speedX,speedY,imgPath,damagePoints);
        super.getAsteroidImage().setScaleX(0.15);
        super.getAsteroidImage().setScaleY(0.15);

    }

    @Override
    public void onCollision() {
        Bullet.decreaseShootableInterval();
    }



}
