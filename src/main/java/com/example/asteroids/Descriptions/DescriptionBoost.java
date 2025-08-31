package com.example.asteroids.Descriptions;

public class DescriptionBoost implements DescriptionInterface{

    private static final String description =
            "The Boost Item is a rare upgrade that enhances your ship's firing capabilities. "
                    + "When collected, it reduces the time between your shots, allowing for faster and more aggressive attacks. "
                    + "Its small size and quick movement make it tricky to catch, but the reward is well worth the effort. "
                    + "This power-up can turn the tide of battle in your favor during intense asteroid encounters.";

    public static String getDescription() {
        return description;
    }


}
