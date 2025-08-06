package com.example.asteroids.BossFights.Bosses;


//eed one instance for the boss, therefore use singletoen here
public class Infernoid {


    //singleton instance
    private Infernoid infernoid;
    //fields
    private double speedX =2.7 ;
    private double speedY = 2.7;
    private int damagePoints = 100;
    private String imgPath = "/imgs/Infernoid.png";
    private String name = "Infernoid";


    private Infernoid(){

    }

    public Infernoid getInfernoid(){
        if(infernoid == null){
            infernoid = new Infernoid();
        }

        return infernoid;
    }
}
