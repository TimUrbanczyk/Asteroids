package com.example.asteroids.Weapons;

public interface iBullet {
    static void drawBullets(){};
    static void moveBullets(){};
    static void spawnBullet(){};
    void toggleActive();
    boolean isActive();
    double getCoordX();
    double getCoordY();
}//end of iBullet
