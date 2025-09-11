package com.example.asteroids.Weapons;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Asteroids.DualityCores;
import com.example.asteroids.Player.Player;
import com.example.asteroids.SoundHandling.MusicPlayer;
import com.example.asteroids.Transaction.PlayerCurrencyHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class Bullet {

    private double coordX;
    private double coordY;
    private final double speed = 30;
    private final double radius = 10;
    private final double angleInRadians;
    private static Player player;
    private Bounds bounds ;
    private double previousX;
    private double previousY;
    private static long shootableInterval = 150;
    private final long spawnTime = System.currentTimeMillis();
    private final String BULLET_SOUND_PATH = "src/main/resources/Sounds/Sounds/BulletSound.mp3";
    private final String HITMARKER_SOUND_PATH = "src/main/resources/Sounds/Sounds/Hitmarker.mp3";
    private final MusicPlayer shootSoundPlayer = new MusicPlayer(BULLET_SOUND_PATH);
    private final MusicPlayer hitmarkerPlayer = new MusicPlayer(HITMARKER_SOUND_PATH);


    public Bullet(double coordX, double coordY, double angle){

        angleInRadians = Math.toRadians(player.getImageView().getRotate()) - (Math.PI / 2);
        this.coordX = coordX;
        this.coordY = coordY;
        this.previousX = coordX;
        this.previousY = coordY;

        this.shootSoundPlayer.setVolume(0.3);
        this.hitmarkerPlayer.setVolume(0.5);

    }

    public void moveBullet() {

        double velocityX = Math.cos(angleInRadians) * this.getSpeed();
        double velocityY = Math.sin(angleInRadians) * this.getSpeed();


        this.previousX = this.getCoordX();
        this.previousY = this.getCoordY();
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
        for (Asteroid asteroid : new ArrayList<>(Asteroid.getAsteroids())) {

            if (asteroid instanceof DualityCores) {
                continue;
            }

            Bounds asteroidBounds = asteroid.getAsteroidImage().getBoundsInParent();

            if (this.bounds.intersects(asteroidBounds) || intersectsSwept(asteroidBounds)) {
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

    private boolean intersectsSwept(Bounds target){
        double x1 = this.previousX;
        double y1 = this.previousY;
        double x2 = this.coordX;
        double y2 = this.coordY;

        // Edges of the target AABB
        double left = target.getMinX();
        double right = target.getMaxX();
        double top = target.getMinY();
        double bottom = target.getMaxY();

        Line2D path = new Line2D.Double(x1 + this.radius / 2.0, y1 + this.radius / 2.0,
                x2 + this.radius / 2.0, y2 + this.radius / 2.0);

        if (target.contains(x2, y2) || target.contains(x1, y1)) {
            return true;
        }

        Line2D topEdge = new Line2D.Double(left, top, right, top);
        Line2D bottomEdge = new Line2D.Double(left, bottom, right, bottom);
        Line2D leftEdge = new Line2D.Double(left, top, left, bottom);
        Line2D rightEdge = new Line2D.Double(right, top, right, bottom);

        return path.intersectsLine(topEdge)
                || path.intersectsLine(bottomEdge)
                || path.intersectsLine(leftEdge)
                || path.intersectsLine(rightEdge);
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
