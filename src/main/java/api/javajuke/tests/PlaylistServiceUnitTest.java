package api.javajuke.tests;

import api.javajuke.data.PlaylistRepository;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.User;
import api.javajuke.service.PlaylistService;
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
        // Create a dummy playlist
        Playlist dummyPlaylist = new Playlist("Playlist 1", new User());
        // Return a stub when findById is called
        when(playlistRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyPlaylist));
        // Try to obtain the playlist
        Playlist playlist = playlistService.getPlaylist(1);
        // Check if the name of the playlist equals "Playlist 1"
        Assert.assertEquals(playlist.getName(), "Playlist 1");
    }

}