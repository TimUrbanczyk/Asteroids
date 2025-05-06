package com.example.asteroids.GUI;

import com.example.asteroids.Player;

public class Healthbar {

    private double coordX;
    private double coordY;
    private final double maxLength = 200;
    private Player player;


    public Healthbar(double coordX, double coordY) {

        this.coordX = coordX;
        this.coordY = coordY;

    }//end of constructor

    public void update() {
        this.coordX = player.getCoordX();
        this.coordY = player.getCoordY();
    }//end of update

    public void attachPlayer(Player player) {
        this.player = player;
    }//end of attachPlayer

    //getters
    public double getCoordX() {return coordX;}

    public double getCoordY() {return coordY;}

    public double getMaxLength() {return maxLength;}

    //setters
    public void setCoordX(double coordX) {this.coordX = coordX;}

    public void setCoordY(double coordY) {this.coordY = coordY;}


}//end of Healthbar
