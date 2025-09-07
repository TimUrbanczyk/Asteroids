package com.example.asteroids.Asteroids;

import com.example.asteroids.Player.Player;

public class Disruptor extends Asteroid{

    private static final double speed = 4;
    private static final int damagePoints = 30;
    private static final String imgPath = "/imgs/Disruptor.png";
    private static final String name = "Disruptor";
    private static Player player;


    public Disruptor(){
        super(speed,speed,imgPath,damagePoints,name);
        super.getAsteroidImage().setFitWidth(60);
        super.getAsteroidImage().setFitHeight(60);
    }

    public static void attachPlayer(Player p){
        player = p;
    }

    @Override
    public void move(){
        double dx = player.getCoordX() - this.getCoordX();
        double dy = player.getCoordY() - this.getCoordY();
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        this.setcoordX(this.getCoordX() + (dx / distance) * speed);
        this.getAsteroidImage().setX(this.getCoordX());
        this.setcoordY(this.getCoordY() + (dy / distance) * speed);
        this.getAsteroidImage().setY(this.getCoordY());
    }
}
