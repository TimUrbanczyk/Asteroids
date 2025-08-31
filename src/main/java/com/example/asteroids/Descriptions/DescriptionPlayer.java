package com.example.asteroids.Descriptions;

public class DescriptionPlayer implements DescriptionInterface {
     private static final String Description =
             "The Player controls a spaceship navigating through the asteroid field. "
            + "It has health points that decrease when colliding with asteroids, "
            + "and can shoot bullets to destroy threats. "
            + "The player can collect items and upgrades to improve survivability and firepower. "
            + "Skillful maneuvering and shooting are key to surviving as long as possible and earning points.";

    public static String getDescription(){
        return Description;
    }
}
