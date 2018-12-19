package api.stenden.res;

import api.stenden.data.model.Playlist;
import api.stenden.exception.EntityNotFoundException;
import api.stenden.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }

    @GetMapping("/playlists")
    public List<Playlist> index() {
        return playlistService.getPlaylists();
    }

    @GetMapping("/playlists/{id}")
    public Playlist index(@PathVariable("id") long id) throws EntityNotFoundException {
        return playlistService.getPlaylist(id);
    }

}
