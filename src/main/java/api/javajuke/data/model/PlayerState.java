package api.javajuke.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class PlayerState {
    private int position;
    private int volume;
    private boolean shuffle;
    private boolean repeat;
    private boolean playing;
    private boolean paused;
    private Track currentTrack;
    private Set<Track> trackList;

    public PlayerState(int position, int volume, boolean shuffle, boolean repeat, boolean playing, boolean paused, Track currentTrack, Set<Track> trackList) {
        this.position = position;
        this.volume = volume;
        this.shuffle = shuffle;
        this.repeat = repeat;
        this.playing = playing;
        this.paused = paused;
        this.currentTrack = currentTrack;
        this.trackList = trackList;
    }
}
