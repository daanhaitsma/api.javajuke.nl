package api.javajuke.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerState {
    private int position;
    private int volume;
    private boolean shuffle;
    private boolean repeat;
    private boolean playing;
    private boolean paused;
    private Track currentTrack;
    private List<Track> trackList;
}
