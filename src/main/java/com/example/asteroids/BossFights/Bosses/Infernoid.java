package com.example.asteroids.BossFights.Bosses;

import com.example.asteroids.Asteroids.Asteroid;
import com.example.asteroids.Player.Player;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Infernoid extends Asteroid {

    private static Infernoid infernoid;
    private static final double speedX = 6;
    private static final double speedY = 6;
    private static final int damagePoints = 100;
    private static final String name = "Infernoid";
    private BossState currentState;
    private double wanderAngle = 0;
    private boolean collisionLeft = false;
    private boolean collisionRight = false;
    private boolean collisionTop = false;
    private boolean collisionDown = false;
    private final Random rng = new Random();


    private Infernoid(){
        super(speedX,speedY,"",damagePoints);
        this.currentState = BossState.WANDER;
        this.setcoordX(495);
        this.setcoordY(32);
        Asteroid.addAsteroid(this);
    }

    public enum BossState {
        WANDER,
        SUMMOM,
        SPLIT,
        DIE
    }

    public void changeState(BossState newState){
        this.currentState = newState;
    }

    public double[] move(){
        switch (currentState){
            case WANDER -> {return moveWander();}
            case SUMMOM -> {moveSummon();}
            case SPLIT -> {moveSplit();}
            case DIE -> {moveDie();}
        }
        return null;
    }


    private double[] moveWander() {

        double currentCoordX = this.getCoordX();
        double currentCoordY = this.getCoordY();


        double jitter = 0.2;
        double deltaTime = 0.16;

        wanderAngle += rng.nextGaussian() * deltaTime * jitter;

        if(collisionLeft){
            wanderAngle = 0;
            collisionLeft = false;
            this.setcoordX(currentCoordX + 15);
        }
        if(collisionRight){
            wanderAngle = Math.PI;
            collisionRight = false;
            this.setcoordX(currentCoordX - 15);
        }
        if(collisionDown){
            wanderAngle = 3 * Math.PI / 2;
            collisionDown = false;
            this.setcoordY(currentCoordY - 15);
        }
        if(collisionTop){
            wanderAngle = Math.PI / 2;
            collisionTop = false;
            this.setcoordY(currentCoordY + 15);
        }

        wanderAngle = (wanderAngle + 2 * Math.PI) % (2 * Math.PI);

        double dx = Math.cos(wanderAngle) * this.getSpeedX() * deltaTime;
        double dy = Math.sin(wanderAngle) * this.getSpeedY() * deltaTime;

        double newcoordX = currentCoordX + dx;
        double newcoordY = currentCoordY + dy;

        this.setcoordX(newcoordX);
        this.setcoordY(newcoordY);
        this.getAsteroidImage().setX(newcoordX);
        this.getAsteroidImage().setY(newcoordY);

        double output[] = {newcoordX,newcoordY};
        return output;

    }

    private void moveSummon(){
        //TODO
    }

    private void moveSplit(){
        //TODO
    }

    private void moveDie(){
        //TODO
    }






    public static Infernoid getInfernoid(){
        if(infernoid == null){
            infernoid = new Infernoid();
        }

        return infernoid;
    }

    public void setCollisionLeft(boolean newstate){
        this.collisionLeft = newstate;
    }
    public void setCollisionRight(boolean newstate){
        this.collisionRight = newstate;
    }
    public void setCollisionTop(boolean newstate){
        this.collisionTop = newstate;
    }
    public void setCollisionDown(boolean newstate){
        this.collisionDown = newstate;
    }

    public boolean isCollisionLeft(){
        return this.collisionLeft;
    }
    public boolean isCollisionRight(){
        return this.collisionRight;
    }
    public boolean isCollisionDown(){
        return this.collisionDown;
    }
    public boolean isCollisionTop(){
        return this.collisionTop ;
    }






}
