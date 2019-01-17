package api.javajuke.tests;

import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.service.MediaplayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;

@ExtendWith(MockitoExtension.class)
public class MediaplayerServiceUnitTest {

    @InjectMocks
    private MediaplayerService mediaplayerService;

    @Test
    public void testPlayPlaylist() {
        // Arrange
        Playlist dummyPlaylist = new Playlist();
        HashSet<Track> tracks = new HashSet<>();
        tracks.add(new Track("C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3"));
        tracks.add(new Track("C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3"));
        tracks.add(new Track("C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3"));
        dummyPlaylist.setTracks(tracks);

        // Act
        mediaplayerService.playPlaylist(dummyPlaylist);

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), "C:/Users/Daan/Documents/__javajuke/mp3/startup.mp3");
    }

}