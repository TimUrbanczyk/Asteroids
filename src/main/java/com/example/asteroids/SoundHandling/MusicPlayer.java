package com.example.asteroids.SoundHandling;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private final String mp3Path;
    private final Media media;
    private final MediaPlayer mediaPlayer;
    private static double volume  = 0.5;
    private static List<MusicPlayer> musicPlayers = new ArrayList<>();

    public MusicPlayer(String URL){
        mp3Path = new File(URL).toURI().toString();
        media = new Media(mp3Path);
        mediaPlayer = new MediaPlayer(media);
        musicPlayers.add(this);
    }

    public void setRepeat(){
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void disableRepeat(){
        mediaPlayer.setCycleCount(1);
    }

    public void playSound(){
        mediaPlayer.play();
    }

    public void stopSound(){
        mediaPlayer.stop();
    }

    private static void refreshVolume(){
        for(MusicPlayer musicPlayer : musicPlayers){
            musicPlayer.mediaPlayer.setVolume(volume);
        }
    }


    public void pauseSound(){
        mediaPlayer.pause();
    }

    public void unpauseSound(){
        mediaPlayer.play();
    }

    public static void setVolume(double newVolume){
        volume = newVolume;
        refreshVolume();
    }
}
