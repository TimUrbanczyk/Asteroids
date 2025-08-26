package com.example.asteroids.BossFights.Bosses;


import com.example.asteroids.Asteroids.Asteroid;

import java.util.Random;

public class Infernoid extends Asteroid {

    private static Infernoid infernoid;
    private static final double speedX =2.7 ;
    private static final double speedY = 2.7;
    private static final int damagePoints = 100;
    private static final String name = "Infernoid";
    private BossState currentState;


    private Infernoid(){
        super(speedX,speedY,"",damagePoints);
        this.currentState = BossState.WANDER;
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

    public void move(){
        switch (currentState){
            case WANDER -> {}
            case SUMMOM -> {}
            case SPLIT -> {}
            case DIE -> {}
        }
    }


    private void moveWander(){
        //TODO
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






}
