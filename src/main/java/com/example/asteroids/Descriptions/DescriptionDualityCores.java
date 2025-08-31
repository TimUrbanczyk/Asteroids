package com.example.asteroids.Descriptions;

public class DescriptionDualityCores implements DescriptionInterface {

    private static final String description =
            "Duality Cores are unique and dangerous asteroids linked by a visible tether. "
                    + "You can only destroy them by maneuvering your ship precisely through the space between the two cores. "
                    + "Attacking them from the side or hitting the cores will not suffice, making timing and positioning crucial. "
                    + "Their coordinated movement and the tether's presence require skill and strategy to overcome, "
                    + "turning every encounter into a tense test of reflexes.";

    public static String getDescription() {
        return description;
    }

}