package api.javajuke.tests;

import api.javajuke.data.PlaylistRepository;
import api.javajuke.data.TrackRepository;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.service.PlaylistService;
import api.javajuke.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceUnitTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @Test
    public void testGetPlaylist() {
        Playlist dummyPlaylist = new Playlist("Playlist 1");
        when(playlistRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyPlaylist));

        Playlist playlist = playlistService.getPlaylist(1);

        Assert.assertEquals(playlist.getName(), "Playlist 1");
    }

}