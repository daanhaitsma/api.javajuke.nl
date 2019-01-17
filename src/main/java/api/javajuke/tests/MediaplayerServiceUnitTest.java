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

    @Test
    public void testPauseSong(){
        Playlist dummyPlaylist = new Playlist();
        HashSet<Track> tracks = new HashSet<>();
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/startup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/startup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/startup.mp3"));
        dummyPlaylist.setTracks(tracks);

        // Act
        mediaplayerService.playPlaylist(dummyPlaylist);
        mediaplayerService.togglePlay();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.isPlaying(), false);
    }

    @Test
    public void testNextSong(){
        Playlist dummyPlaylist = new Playlist();
        HashSet<Track> tracks = new HashSet<>();
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/middleup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/startup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/endup.mp3"));
        dummyPlaylist.setTracks(tracks);

        // Act
        mediaplayerService.playPlaylist(dummyPlaylist);
        mediaplayerService.nextSong();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), "C:/Users/viper/Desktop/api.javajuke/middleup.mp3");
    }

    @Test
    public void testPreviousSong(){
        Playlist dummyPlaylist = new Playlist();
        HashSet<Track> tracks = new HashSet<>();
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/startup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/endup.mp3"));
        tracks.add(new Track("C:/Users/viper/Desktop/api.javajuke/middleup.mp3"));
        dummyPlaylist.setTracks(tracks);

        // Act
        mediaplayerService.playPlaylist(dummyPlaylist);
        mediaplayerService.previousSong();

        // Assert
        Assert.assertEquals(mediaplayerService.getTrackList(), tracks);
        Assert.assertEquals(mediaplayerService.getCurrentTrack().getPath(), "C:/Users/viper/Desktop/api.javajuke/endup.mp3");
    }
}