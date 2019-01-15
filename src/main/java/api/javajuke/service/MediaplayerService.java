package api.javajuke.service;

import api.javajuke.data.model.PlayerState;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import jaco.mp3.player.MP3Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class MediaplayerService {

    @Autowired
    private Environment env;

    Thread playerThread;

    MP3Player mp3Player;

    public MediaplayerService(){

    }

    public PlayerState getPlayerState() {
        return new PlayerState(
                this.getPosition(),
                this.getVolume(),
                this.getShuffle(),
                this.getRepeat(),
                this.mp3Player.isPlaying(),
                this.mp3Player.isPaused());
    }

    //Checks if the mp3Player is playing or paused
    //Starts a new thread in which the mp3Player gets instantiated
    //If both booleans are false, the thread will start else it will start playing the current song
    public void playMusic() {
        if(!mp3Player.isPlaying()) {
            mp3Player.play();
        }
        else{
            mp3Player.pause();
        }
    }

    public void playPlaylist(Playlist playlist) {


        Iterator<Track> trackIterator = playlist.getTracks().iterator();
        ArrayList<File> files = new ArrayList<>();
        while (trackIterator.hasNext()) {
            Track track = trackIterator.next();
            files.add(new File(track.getPath()));
        }

        Runnable playerRunnable = new PlayerRunnable(files.toArray(new File[files.size()]));
        playerThread = new Thread(playerRunnable);
        playerThread.start();

    }

    //Stops the mp3Player and closes the thread
    public void stopMusic(){
        if(mp3Player.isPlaying() || mp3Player.isPaused()){
            mp3Player.stop();
        }
    }

    //Plays the next song on the playlist
    public void nextSong(){
        playMusic();
        mp3Player.skipForward();
    }

    //Plays the previous song on the playlist
    public void previousSong(){
        playMusic();
        mp3Player.skipBackward();
    }

    //Shuffles the playlist
    public boolean getShuffle(){
        return mp3Player.isShuffle();
    }

    //Shuffles the playlist
    public void toggleShuffle(){
        if(this.getShuffle()){
            mp3Player.setShuffle(false);
        }else{
            mp3Player.setShuffle(true);
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

    public int getVolume() {
        return mp3Player.getVolume();
    }
    
    public void setRepeat(){
        if(mp3Player.isRepeat()){
            mp3Player.setRepeat(false);
        }
        else {
            mp3Player.setRepeat(true);
        }
    }

    public boolean getRepeat(){
        return mp3Player.isRepeat();
    }

    class PlayerRunnable implements Runnable{

        private File[] files;

        public PlayerRunnable(File[] files) {
            this.files = files;
        }

        //Instantiates the mp3Player and plays the current song
        public void run(){
            try {
                MediaplayerService.this.mp3Player = new MP3Player(files);
                MediaplayerService.this.stopMusic();
                MediaplayerService.this.mp3Player.play();
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
