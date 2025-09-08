package com.example.asteroids.Descriptions;

public class DescriptionDisruptor implements DescriptionInterface {

    private static final String description =
            "The Disruptor is a menacing asteroid that actively tracks your ship, making it one of the most dangerous obstacles in the field. "
                    + "With its moderate speed and high damage potential, it forces players to stay alert and constantly maneuver. "
                    + "Its compact design and aggressive movement pattern make it difficult to avoid, punishing any lapse in concentration. "
                    + "Surviving encounters with the Disruptor requires quick reflexes and strategic positioning.";

    public static String getDescription() {
        return description;
    }
}