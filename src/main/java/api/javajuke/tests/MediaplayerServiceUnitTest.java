package api.javajuke.tests;

import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.service.MediaplayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class MediaplayerServiceUnitTest {

    @InjectMocks
    private MediaplayerService mediaplayerService;

    private Playlist dummyPlaylist;
    private HashSet<Track> tracks;

    @BeforeEach
    public void setup() {
        dummyPlaylist = new Playlist();
        tracks = new HashSet<>();
        tracks.add(new Track("C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3"));
        dummyPlaylist.setTracks(tracks);
        mediaplayerService.playPlaylist(dummyPlaylist);
    }

    @Test
    public void testPlayPlaylist() {
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), "C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3");
    }

    @Test
    public void testSetVolume() {
        mediaplayerService.setVolume(40);

        Assert.assertEquals(mediaplayerService.getVolume(), 40);
    }

    @Test
    public void testSetVolumeVolumeCannotBeMoreThanHundred() {
        mediaplayerService.setVolume(50);

        assertThatThrownBy(() -> {
            mediaplayerService.setVolume(101);
        }).isOfAnyClassIn(IllegalArgumentException.class);
        Assert.assertEquals(mediaplayerService.getVolume(), 50);
    }

    @Test
    public void testSetVolumeVolumeCannotBeNegative() {
        mediaplayerService.setVolume(50);

        assertThatThrownBy(() -> {
            mediaplayerService.setVolume(-1);
        }).isOfAnyClassIn(IllegalArgumentException.class);
        Assert.assertEquals(mediaplayerService.getVolume(), 50);
    }

    @Test
    public void testToggleRepeat() {
        Assert.assertEquals(mediaplayerService.isRepeated(), true);

        mediaplayerService.toggleRepeat();

        Assert.assertEquals(mediaplayerService.isRepeated(), false);
    }

}