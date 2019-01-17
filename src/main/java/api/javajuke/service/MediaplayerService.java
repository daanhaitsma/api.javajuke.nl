package api.javajuke.service;

import api.javajuke.data.model.PlayerState;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import jaco.mp3.player.MP3Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
public class MediaplayerService {

    @Autowired
    private Environment env;

    private final TrackService trackService;

    private MP3Player mp3Player;
    private Set<Track> trackList;
    private Set<Track> originalTrackList;
    private boolean isShuffled = false;

    @Autowired
    public MediaplayerService(TrackService trackService)
    {
        this.trackService = trackService;
    }

    public PlayerState getPlayerState() {
        return new PlayerState(
                mp3Player.getPosition(),
                mp3Player.getVolume(),
                isShuffled,
                mp3Player.isRepeat(),
                mp3Player.isPlaying(),
                mp3Player.isPaused(),
                getCurrentTrack(),
                trackList);

    }

    public void togglePlay() {
        if (mp3Player.isPlaying()) {
            // Currently playing; pause
            mp3Player.pause();
        } else {
            // Currently paused; play
            mp3Player.play();
        }
    }

    public void stopMusic() {
        mp3Player.stop();
    }

    public void playPlaylist(Playlist playlist) {
        // Store the playlist tracks as both the new and original trackList
        trackList = playlist.getTracks();
        originalTrackList = trackList;
        // Play the given playlist
        playTrackList();
    }

    private void playTrackList() {
        // If a track is currently playing, stop it
        if (mp3Player != null) {
            mp3Player.stop();
        }

        // Iterate over the tracklist, adding them to the mp3player
        Iterator<Track> trackIterator = trackList.iterator();
        ArrayList<File> files = new ArrayList<>();
        while (trackIterator.hasNext()) {
            Track track = trackIterator.next();
            files.add(track.getFile());
        }

        // Create the mp3player
        mp3Player = new MP3Player(files.toArray(new File[files.size()]));

        // Start a new thread which will play the music
        Runnable playerRunnable = new PlayerRunnable(mp3Player);
        Thread playerThread = new Thread(playerRunnable);
        playerThread.start();
    }

    // Plays the next song in the tracklist
    public void nextSong() {
        if (mp3Player.isPaused()) {
            mp3Player.play();
        }

        mp3Player.skipForward();
    }

    // Plays the previous song in the tracklist
    public void previousSong() {
        mp3Player.skipBackward();
    }

    // Adds the specified track to the queue at the specified position
    public void addTrackToQueue(long trackID) {
        int position = mp3Player.getPlayingIndex() + 1;
        Track track = trackService.getTrack(trackID);

        List<Track> trackListList = new ArrayList<>(trackList);
        trackListList.add(position, track);
        trackList = new LinkedHashSet<>(trackListList);

        mp3Player.addAtIndex(position, track.getFile());
    }

    // Shuffles the playlist
    public void toggleShuffle() {
        if (isShuffled) {
            // Is currently shuffled; reset
            trackList = originalTrackList;
        } else {
            // Not currently shuffled; shuffle

            // Convert Set to List
            List<Track> trackListList = new ArrayList<>(trackList);
            // Shuffle List
            Collections.shuffle(trackListList);
            // Convert List back to Set
            trackList = new LinkedHashSet<>(trackListList);
        }

        isShuffled = !isShuffled;
        // Play the new tracklist
        playTrackList();
    }

    // Sets the volume of the mp3Player
    public void setVolume(int volume) {
        mp3Player.setVolume(volume);
    }
    
    public void toggleRepeat() {
        if (mp3Player.isRepeat()) {
            mp3Player.setRepeat(false);
        } else {
            mp3Player.setRepeat(true);
        }
    }

    public Track getCurrentTrack() {
        if(mp3Player.isStopped()){
            return null;
        }
        List<Track> trackListList = new ArrayList<>(trackList);
        return trackListList.get(mp3Player.getPlayingIndex());
    }

    public Set<Track> getTrackList() {
        return trackList;
    }

    public boolean isPlaying() {
        return mp3Player.isPlaying();
    }

    public boolean isPaused() {
        return mp3Player.isPaused();
    }

    public boolean isStopped() {
        return mp3Player.isStopped();
    }

    public boolean isRepeated() {
        return mp3Player.isRepeat();
    }

    public int getVolume() {
        return mp3Player.getVolume();
    }

    class PlayerRunnable implements Runnable{

        private MP3Player mp3Player;

        public PlayerRunnable(MP3Player mp3Player) {
            this.mp3Player = mp3Player;
        }

        // Instantiates the mp3Player and plays the current song
        public void run(){
            try {
                mp3Player.stop();
                mp3Player.play();
                // Keeps the thread active until the player has finished
                while(!mp3Player.isStopped()){
                    Thread.sleep(5000);
                }
            }catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}
