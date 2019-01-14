package api.javajuke.service;

import jaco.mp3.player.MP3Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.io.File;

@Service
public class MediaplayerService {

    @Autowired
    private Environment env;

    MP3Player mp3Player;
    PlayerThread player_thread;
    boolean isPlaying = false;
    boolean isPaused = false;

    public boolean isPlaying() {
        return isPlaying;
    }
    public boolean isPaused() {
        return isPaused;
    }

    public MediaplayerService(){

    }

    //Checks if the mp3Player is playing or paused
    //Starts a new thread in which the mp3Player gets instantiated
    //If both booleans are false, the thread will start else it will start playing the current song
    public void playMusic() {
        if (!isPlaying && !isPaused) {
            player_thread = new PlayerThread();
            player_thread.start();
        } else if(isPlaying) {

        }else
        {
            mp3Player.play();
            isPlaying = true;
            isPaused = false;
        }

    }

    //Checks if mp3Player is currently playing music
    //If true the mp3Player will pause
    public void pauseMusic(){
        if(isPlaying){
            mp3Player.pause();
            isPlaying = false;
            isPaused = true;
        }
    }

    //Stops the mp3Player and closes the thread
    public void stopMusic(){
        if(isPlaying || isPaused){
            mp3Player.stop();
            isPlaying = false;
            isPaused = false;
            player_thread = null;
        }
    }

    //Plays the next song on the playlist
    public void nextSong(){
        mp3Player.skipForward();
    }

    //Plays the previous song on the playlist
    public void previousSong(){
        mp3Player.skipBackward();
    }

    //Shuffles the playlist
    public void shuffle(){
        if(mp3Player.isShuffle()){
            mp3Player.setShuffle(true);
        }else{
            mp3Player.setShuffle(false);
        }
    }
    //Sets the volume of the mp3Player
    public void setVolume(int volume){
        mp3Player.setVolume(volume);
    }

    public void addSong(){
        mp3Player.add(new File(env.getProperty("debug.song.path2")));
    }

    //Adds a song to the playlist
    public void addToPlaylist(File file){
        mp3Player.add(file);
    }

    public int getPosition() {
        return mp3Player.getPosition();
    }

    public void setRepeat(){
        if(mp3Player.isRepeat()){
            mp3Player.setRepeat(false);
        }
        else {
            mp3Player.setRepeat(true);
        }
    }

    class PlayerThread extends Thread{
        //Instantiates the mp3Player and plays the current song
        public void run(){
            try {
                MediaplayerService.this.mp3Player = new MP3Player(new File[]{new File(env.getProperty("debug.song.path"))});
                MediaplayerService.this.mp3Player.play();
                isPlaying = true;
                isPaused = false;
                //Waits till the song has ended and puts the thread to sleep
                while(!MediaplayerService.this.mp3Player.isStopped()){
                    Thread.sleep(5000);
                }
            }catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}
