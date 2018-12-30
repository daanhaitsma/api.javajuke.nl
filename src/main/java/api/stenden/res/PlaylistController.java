package api.stenden.res;

import api.stenden.data.model.Playlist;
import api.stenden.exception.BadRequestException;
import api.stenden.exception.EntityNotFoundException;
import api.stenden.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }

    @GetMapping("/playlists")
    public List<Playlist> show() {
        return playlistService.getPlaylists();
    }

    @PostMapping(value = "/playlists", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist create(@RequestParam Map<String, String> body) {
        String name = body.get("name");

        return playlistService.createPlaylist(name);
    }

    @GetMapping("/playlists/{id}")
    public Playlist show(@PathVariable("id") long id) throws EntityNotFoundException {
        return playlistService.getPlaylist(id);
    }

    @PutMapping(value = "/playlists/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist update(@PathVariable("id") long id, @RequestBody MultiValueMap<String, String> body) throws EntityNotFoundException, BadRequestException {
        String name = body.getFirst("name");

        return playlistService.updatePlaylist(id, name);
    }

    @DeleteMapping("/playlists/{id}")
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException {
        playlistService.deletePlaylist(id);
    }

    @PostMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist addTrackToPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.addTrackToPlaylist(id, trackId);
    }

    @DeleteMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist removeTrackFromPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.removeTrackFromPlaylist(id, trackId);
    }
}
