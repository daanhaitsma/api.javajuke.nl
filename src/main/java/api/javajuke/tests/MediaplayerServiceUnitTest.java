package api.javajuke.tests;

import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.service.MediaplayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.io.IOException;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class MediaplayerServiceUnitTest {

    @InjectMocks
    private MediaplayerService mediaplayerService;

    private Track trackStartup, trackMiddleup, trackEndup;

    private Playlist dummyPlaylist;
    private HashSet<Track> tracks;

    @BeforeEach
    public void setup() throws IOException {
        dummyPlaylist = new Playlist();
        tracks = new HashSet<>();

        // Create dummy tracks
        trackStartup = new Track(new ClassPathResource("startup.mp3").getURL().getPath());
        trackMiddleup = new Track(new ClassPathResource("middleup.mp3").getURL().getPath());
        trackEndup = new Track(new ClassPathResource("endup.mp3").getURL().getPath());

        // Add tracks to playlist
        tracks.add(trackStartup);
        tracks.add(trackMiddleup);
        tracks.add(trackEndup);
        dummyPlaylist.setTracks(tracks);

        // Start the player
        mediaplayerService.playPlaylist(dummyPlaylist);
    }

    @Test
    public void testPlayPlaylist() {
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackStartup.getPath());
    }

    @Test
    public void testSetVolume() {
        mediaplayerService.setVolume(50);
        Assert.assertEquals(mediaplayerService.getVolume(), 50);

        mediaplayerService.setVolume(40);
        Assert.assertEquals(mediaplayerService.getVolume(), 40);

        mediaplayerService.setVolume(0);
        Assert.assertEquals(mediaplayerService.getVolume(), 0);
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

    @Test
    public void testPauseSong(){
        // Act
        mediaplayerService.togglePlay();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.isPlaying(), false);
    }

    @Test
    public void testNextSong(){
        // Act
        mediaplayerService.nextSong();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackStartup.getPath());
    }

    @Test
    public void testPreviousSong(){
        // Act
        mediaplayerService.previousSong();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackMiddleup.getPath());
    }
}