package com.example.asteroids.Transaction;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class PlayerCurrencyHandler {

    private static ObjectMapper playerWalletMapper  = new ObjectMapper();
    private static final String playerWalletFilePath = "src/main/java/com/example/asteroids/Transaction/PlayerWallet.json";
    private static File playerWalletFile = new File(playerWalletFilePath);
    private static final ObjectNode jsonNode;
    private static int playerSpaceCoins;


    static{
        try {
            jsonNode = (ObjectNode) playerWalletMapper.readTree(playerWalletFile);
            playerSpaceCoins = jsonNode.get("SpaceCoins").asInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void increasePlayerSpaceCoins(int spaceCoins){
        try{
            int newPlayerSpaceCoins = playerSpaceCoins + spaceCoins;
            playerSpaceCoins = newPlayerSpaceCoins;
            jsonNode.put("SpaceCoins", newPlayerSpaceCoins);
            playerWalletMapper.writerWithDefaultPrettyPrinter().writeValue(playerWalletFile, jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static int getPlayerSpaceCoins(){
        return playerSpaceCoins;
    }
}
