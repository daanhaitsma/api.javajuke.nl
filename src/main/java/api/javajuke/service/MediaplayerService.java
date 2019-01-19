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
    private int volume = 50;

    @Autowired
    public MediaplayerService(TrackService trackService)
    {
        this.trackService = trackService;
    }

    /**
     * Gets all the data from the mp3Player (position of track,
     * volume of mp3Player, shuffled state, repeat playlist state,
     * playing state, paused state, current track, current playlist)
     *
     * @return object with the different statuses from the mediaplayer
     */
    public PlayerState getPlayerState() {
        if (isStopped()) {
            return new PlayerState(
                    0,
                    this.volume,
                    false,
                    true,
                    false,
                    false,
                    null,
                    null
            );
        } else {
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
    }

    /**
     * Toggles the mp3Player between playing and pause
     */
    public void togglePlay() {
        if (mp3Player.isPlaying()) {
            // Currently playing; pause
            pauseMusic();
        } else {
            // Currently paused; play
            playMusic();
        }
    }

    /**
     * Plays the mp3Player
     */
    private void playMusic() {
        mp3Player.play();
    }

    /**
     * Pauses the mp3Player
     */
    private void pauseMusic() {
        mp3Player.pause();
    }
    
    /**
     * Stops the mp3Player
     */
    public void stopMusic() {
        mp3Player.stop();
    }

    /**
     * Collects all the tracks for the playList and plays them
     * @param playlist with all tracks
     */
    public void playPlaylist(Playlist playlist) {
        // Store the playlist tracks as both the new and original trackList
        trackList = playlist.getTracks();
        originalTrackList = trackList;

        if (isShuffled) {
            toggleShuffleOn();
        }

        // Play the given playlist
        playTrackList();
    }

    /**
     * Stops the mediaplayer and creates a new mediaplayer object with all the tracks
     */
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

        File[] filesArray = files.toArray(new File[files.size()]);

        // Create the mp3player
        mp3Player = new MP3Player(filesArray);
        setVolume(volume);
        // Start a new thread which will play the music
        Runnable playerRunnable = new PlayerRunnable(mp3Player);
        Thread playerThread = new Thread(playerRunnable);
        playerThread.start();
    }

    /**
     * Plays the next song in the tracklist, if a song is paused play it and skip to next song
     */
    public void nextSong() {
        if (mp3Player.isPaused()) {
            mp3Player.play();
        }

        mp3Player.skipForward();
    }

    /**
     * Plays the previous song in the tracklist
     */
    public void previousSong() {
        mp3Player.skipBackward();
    }

    /**
     * Adds the specified track to the queue behind the track that is currently playing
     *
     * @param trackID trackId from database
     */
    public void addTrackToQueue(long trackID) {
        Track track = trackService.getTrack(trackID);
        if (mp3Player == null) {
            trackList = new LinkedHashSet<>();
            trackList.add(track);
            playTrackList();
        } else {
            int position = mp3Player.getPlayingIndex() + 1;
            List<Track> trackListList = new ArrayList<>(trackList);
            trackListList.add(position, track);
            trackList = new LinkedHashSet<>(trackListList);

            mp3Player.addAtIndex(position, track.getFile());
        }
    }

    /**
     * Checks if the playlist is shuffled, if not shuffles the playlist
     */
    public void toggleShuffle() {
        if (isShuffled) {
            // Is currently shuffled; reset
            toggleShuffleOff();
        } else {
            // Not currently shuffled; shuffle
            toggleShuffleOn();
        }

        isShuffled = !isShuffled;
        // Play the new tracklist
        playTrackList();
    }

    private void toggleShuffleOn() {
        // Convert Set to List
        List<Track> trackListList = new ArrayList<>(trackList);
        // Shuffle List
        Collections.shuffle(trackListList);
        // Convert List back to Set
        trackList = new LinkedHashSet<>(trackListList);
    }

    private void toggleShuffleOff() {
        trackList = originalTrackList;
    }

    /**
     * Sets the volume of the mp3Player
     *
     * @param newVolume specified volume
     */
    public void setVolume(int newVolume) {
        this.volume = newVolume;
        mp3Player.setVolume(this.volume);
    }

    /**
     * Checks if repeat is true
     */
    public void toggleRepeat() {
        if (mp3Player.isRepeat()) {
            mp3Player.setRepeat(false);
        } else {
            mp3Player.setRepeat(true);
        }
    }

    /**
     * Checks if the mp3Player is running, returns null if not running
     * and the current track if it is running
     *
     * @return current track
     */
    public Track getCurrentTrack() {
        if(mp3Player.isStopped()){
            return null;
        }
        List<Track> trackListList = new ArrayList<>(trackList);
        return trackListList.get(mp3Player.getPlayingIndex());
    }

    /**
     * Get a set of tracklist
     *
     * @return tracklist
     */
    public Set<Track> getTrackList() {
        return trackList;
    }

    /**
     * Checks the isPaused and isStopped boolean from the mp3Player
     *
     * @return isPlaying boolean
     */
    public boolean isPlaying() {
        return mp3Player.isPlaying();
    }

    public boolean isPaused() {
        return mp3Player.isPaused();
    }

    public boolean isStopped() {
        if (mp3Player == null) {
            return true;
        }
        return mp3Player.isStopped();
    }

    public boolean isRepeated() {
        return mp3Player.isRepeat();
    }

    /**
     * Checks for the volume of the mp3Player
     *
     * @return int volume
     */
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
