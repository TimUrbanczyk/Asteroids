package com.example.asteroids.GUI;


//use singleton here

import javafx.scene.control.Label;

public class LabelAsteroids {

    //singleton instance
    private static LabelAsteroids instance;

    //fields
    private Label labelAsteroids;

    //constructor
    private LabelAsteroids(){}

    public static LabelAsteroids getInstance(){
        if(instance == null){ instance = new LabelAsteroids(); }
        return instance;
    }//end of getInstance

    public Label getLabelAsteroids(){return this.labelAsteroids;}




}//end of LabelAsteroids
