package api.javajuke.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PlayerState {
    private int position;
    private int volume;
    private boolean shuffle;
    private boolean repeat;
    private boolean playing;
    private boolean paused;

    public PlayerState(int position, int volume, boolean shuffle, boolean repeat, boolean playing, boolean paused) {
        this.position = position;
        this.volume = volume;
        this.shuffle = shuffle;
        this.repeat = repeat;
        this.playing = playing;
        this.paused = paused;
    }
}
