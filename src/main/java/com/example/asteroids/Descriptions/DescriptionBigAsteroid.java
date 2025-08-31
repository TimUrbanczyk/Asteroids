package com.example.asteroids.Descriptions;

public class DescriptionBigAsteroid implements DescriptionInterface {

    private static final String Description =
                    "The Big Asteroid is a massive space rock that moves slower than smaller asteroids, "
                    + "but causes significantly more damage upon impact. "
                    + "Its large size makes it easier to hit, but also harder to avoid. "
                    + "In the game, it adds a challenging element due to its high damage potential and randomized movement speed.";

    public static String getDescription(){
        return Description;
    }


}
