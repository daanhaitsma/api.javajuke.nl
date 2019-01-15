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
import java.util.*;

@Service
public class MediaplayerService {

    @Autowired
    private Environment env;

    Thread playerThread;

    MP3Player mp3Player;

    Set<Track> trackList;
    Set<Track> originalTrackList;

    boolean isShuffled = false;

    public MediaplayerService(){

    }

    public PlayerState getPlayerState() {
        return new PlayerState(
                this.getPosition(),
                this.getVolume(),
                this.getShuffle(),
                this.getRepeat(),
                this.mp3Player.isPlaying(),
                this.mp3Player.isPaused(),
                this.getCurrentTrack(),
                this.getTracklist());

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
        trackList = playlist.getTracks();
        originalTrackList = trackList;
        playTrackList(playlist.getTracks());
    }

    public void playTrackList(Set<Track> trackList) {
        if (mp3Player != null) {
            mp3Player.stop();
        }
        Iterator<Track> trackIterator = trackList.iterator();
        ArrayList<File> files = new ArrayList<>();
        while (trackIterator.hasNext()) {
            Track track = trackIterator.next();
            files.add(new File(track.getPath()));
        }

        mp3Player = new MP3Player(files.toArray(new File[files.size()]));
        Runnable playerRunnable = new PlayerRunnable();
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
        mp3Player.skipForward();
    }

    //Plays the previous song on the playlist
    public void previousSong(){
        mp3Player.skipBackward();
    }

    //Shuffles the playlist
    public boolean getShuffle(){
        return isShuffled;
    }

    //Shuffles the playlist
    public void toggleShuffle(){
        if(this.isShuffled){
            // Is currently shuffled; reset
            trackList = originalTrackList;
        }else{
            // Not currently shuffled; shuffle

            // Convert Set to List
            List<Track> trackListList = new ArrayList<>(trackList);
            // Shuffle List
            Collections.shuffle(trackListList);
            // Convert List back to Set
            trackList = new LinkedHashSet<>(trackListList);
        }

        isShuffled = !isShuffled;
        playTrackList(trackList);
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

    public Track getCurrentTrack() {
        List<Track> trackListList = new ArrayList<>(trackList);
        return trackListList.get(mp3Player.getPlayingIndex());
    }

    public Set<Track> getTracklist() {
        return trackList;
    }

    class PlayerRunnable implements Runnable{

        private File[] files;

        //Instantiates the mp3Player and plays the current song
        public void run(){
            try {
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
