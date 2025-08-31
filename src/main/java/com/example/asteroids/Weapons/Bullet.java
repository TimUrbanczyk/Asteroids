package com.example.asteroids.Weapons;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Asteroids.DualityCores;
import com.example.asteroids.Player.Player;
import com.example.asteroids.SoundHandling.MusicPlayer;
import com.example.asteroids.Transaction.PlayerCurrencyHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.io.IOException;

public class Bullet {

    private double coordX;
    private double coordY;
    private final double speed = 60;
    private final double radius = 20;
    private final double angle;
    private final boolean isActive = true;
    private final double angleInRadians;
    private static Player player;
    private Bounds bounds ;
    private static final boolean shootableFlag = true;
    private static long shootableInterval = 150;
    private final long spawnTime = System.currentTimeMillis();
    private final MusicPlayer shootSoundPlayer = new MusicPlayer("src/main/resources/Sounds/Sounds/BulletSound.mp3");
    private final MusicPlayer hitmarkerPlayer = new MusicPlayer("src/main/resources/Sounds/Sounds/Hitmarker.mp3");


    public Bullet(double coordX, double coordY, double angle){

        angleInRadians = Math.toRadians(player.getImageView().getRotate()) - (Math.PI / 2);
        this.coordX = coordX;
        this.coordY = coordY;
        this.angle = angle;
        this.bounds = new BoundingBox(
            this.coordX,
            this.coordY,
            this.radius,
            this.radius
        );

        this.shootSoundPlayer.setVolume(0.3);
        this.hitmarkerPlayer.setVolume(0.5);

    }

    public void moveBullet() {

        double velocityX = Math.cos(angleInRadians) * this.getSpeed();
        double velocityY = Math.sin(angleInRadians) * this.getSpeed();


        this.setCoordX(this.getCoordX() + velocityX);
        this.setCoordY(this.getCoordY() + velocityY);

        this.bounds = new BoundingBox(
                this.coordX,
                this.coordY,
                this.radius,
                this.radius
        );

    }

    public boolean checkCollision() throws IOException {

        for(Asteroid asteroid : Asteroid.getAsteroids()) {

            if(asteroid instanceof DualityCores){
                break;
            }

            Bounds asteroidBounds = new BoundingBox(
                    asteroid.getAsteroidImage().getBoundsInParent().getMinX(),
                    asteroid.getAsteroidImage().getBoundsInParent().getMinY(),
                    asteroid.getAsteroidImage().getBoundsInParent().getWidth(),
                    asteroid.getAsteroidImage().getBoundsInParent().getHeight()
            );


            if(this.bounds.intersects(asteroidBounds)){

                Player.addAsteroidToDiscoveredEntities(asteroid);

                hitmarkerPlayer.disableRepeat();
                hitmarkerPlayer.playSound();

                asteroid.removeImage();
                asteroid.despawnAsteroid();
                player.getBullets().remove(this);
                PlayerCurrencyHandler.increasePlayerSpaceCoins(1);

                return true;
            }
        }
        return false;
    }
    public static void attachPlayer(Player p){
        player = p;
    }


    public static void spawnBullet(){

        if(player.getBullets().size() > 9){ for(int i = 0; i <5;i++){player.getBullets().removeFirst();}}

        Bullet newBullet = new Bullet(
                player.getImageView().getBoundsInParent().getCenterX()-18,
                        player.getImageView().getBoundsInParent().getCenterY(),
                        player.getImageView().getRotate());

        player.getBullets().push(newBullet);

        newBullet.shootSoundPlayer.disableRepeat();
        newBullet.shootSoundPlayer.playSound();

    }

    public static void despawnBulletByTime(){

        for(Bullet bullet : player.getBullets()){

            if(System.currentTimeMillis() - bullet.spawnTime > 5000){
                player.getBullets().remove(bullet);
                break;
            }

        }

    }

    public double getCoordX(){
        return this.coordX;
    }

    public double getCoordY(){
        return this.coordY;
    }

    public double getRadius(){
        return this.radius;
    }

    public double getSpeed(){
        return this.speed;
    }

    public static long getShootableInterval(){
        return shootableInterval;
    }

    public void setCoordX(double coordX){
        this.coordX = coordX;
    }

    public void setCoordY(double coordY){
        this.coordY = coordY;
    }

    public static void decreaseShootableInterval(){
        if(shootableInterval > 50) {
            shootableInterval -= 5;
        }
    }


}
