package com.example.asteroids.SoundHandling;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class MusicPlayer {
    private String mp3Path;
    private Media media;
    private MediaPlayer mediaPlayer;

    public MusicPlayer(String URL){
        mp3Path = new File(URL).toURI().toString();
        media = new Media(mp3Path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void playSound(){
        mediaPlayer.play();
    }

    public void stopSound(){
        mediaPlayer.stop();
    }
}
