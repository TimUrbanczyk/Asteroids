package com.example.asteroids.Items;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Weapons.Bullet;

public class BoostItem extends Asteroid implements ItemInterface{

    private static double speedX =3.5;
    private static double speedY =3.5;
    private static final int damagePoints = 0;
    private static final String imgPath = "/imgs/BoostItem.png";
    private static final String name = "BoostUpgread";


    public BoostItem(){
        super(speedX,speedY,imgPath,damagePoints,name);
        super.getAsteroidImage().setFitWidth(60);
        super.getAsteroidImage().setFitHeight(60);

    }

    @Override
    public void onCollision() {
        Bullet.decreaseShootableInterval();
    }


}
