package com.example.asteroids.Level;


//use singleton here

import javafx.scene.control.Label;

public class LabelOrbitbreaker {

    //singleton instance
    private static LabelOrbitbreaker instance;

    //fields
    private Label labelOrbitbreaker;

    //constructor
    private LabelOrbitbreaker(){}

    public static LabelOrbitbreaker getInstance(){
        if(instance == null){ instance = new LabelOrbitbreaker(); }
        return instance;
    }//end of getInstance

    public Label getLabelOrbitbreaker(){return this.labelOrbitbreaker;}




}//end of LabelAsteroids
