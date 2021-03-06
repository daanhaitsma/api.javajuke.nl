package api.javajuke.tests;

import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.service.MediaplayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MediaplayerServiceUnitTest {

    @InjectMocks
    private MediaplayerService mediaplayerService;

    @Mock
    private Track trackStartup, trackMiddleup, trackEndup;

    private Playlist dummyPlaylist;
    private List<Track> tracks;

    @BeforeEach
    public void setup() throws IOException{
        dummyPlaylist = new Playlist();

        tracks = new ArrayList<>();

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
        // Compare the getTrackList method results to all manually added tracks
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        // Compare the path of the current track to the path of the first playing track
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackStartup.getPath());
    }

    @Test
    public void testSetVolume() {
        // Set volume to 50 and compare this with the getVolume() method
        mediaplayerService.setVolume(50);
        Assert.assertEquals(mediaplayerService.getVolume(), 50);

        // Set volume to 40 and compare this with the getVolume() method
        mediaplayerService.setVolume(40);
        Assert.assertEquals(mediaplayerService.getVolume(), 40);

        // Set volume to 0 and compare this with the getVolume() method
        mediaplayerService.setVolume(0);
        Assert.assertEquals(mediaplayerService.getVolume(), 0);
    }

    @Test
    public void testSetVolumeVolumeCannotBeMoreThanHundred() {
        // Set volume to 50
        mediaplayerService.setVolume(50);

        // Try to set volume to 101(not possible)
        assertThatThrownBy(() -> {
            mediaplayerService.setVolume(101);
        }).isOfAnyClassIn(IllegalArgumentException.class);

        // Check if volume is still 50
        Assert.assertEquals(mediaplayerService.getVolume(), 50);
    }

    @Test
    public void testSetVolumeVolumeCannotBeNegative() {
        // Set volume to 50
        mediaplayerService.setVolume(50);

        //Try to set volume to -1(not possible)
        assertThatThrownBy(() -> {
            mediaplayerService.setVolume(-1);
        }).isOfAnyClassIn(IllegalArgumentException.class);

        // Check if volume is still 50
        Assert.assertEquals(mediaplayerService.getVolume(), 50);
    }

    @Test
    public void testToggleRepeat() {
        // Check if repeat option is enabled
        Assert.assertEquals(mediaplayerService.isRepeated(), true);

        // Toggle repeat option
        mediaplayerService.toggleRepeat();

        // Check if repeat is disabled
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
    public void testNextSong() throws Exception{
        // Act
        mediaplayerService.nextSong();
        //Use sleep to give the thread enough time to load otherwise the test will fail (Threading issue)
        sleep(1000);
        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackMiddleup.getPath());
    }

    @Test
    public void testPreviousSong() throws Exception{
        // Act
        mediaplayerService.previousSong();
        //Use sleep to give the thread enough time to load otherwise the test will fail (Threading issue)
        sleep(1000);
        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), trackEndup.getPath());
    }
}