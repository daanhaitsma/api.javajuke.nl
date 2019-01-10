package api.javajuke.res;

import api.javajuke.data.model.Playlist;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    /**
     * Constructor for the PlaylistController class.
     *
     * @param playlistService the playlist service containing all playlist logic
     */
    @Autowired
    public PlaylistController(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }

    /**
     * Creates an endpoint that returns a json response with all playlists.
     *
     * @return all playlists as a json response
     */
    @GetMapping("/playlists")
    public List<Playlist> show() {
        return playlistService.getPlaylists();
    }

    /**
     * Creates an endpoint that creates a new playlist.
     *
     * @return the newly created playlist as a json response
     */
    @PostMapping(value = "/playlists", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist create(@RequestParam Map<String, String> body) {
        String name = body.get("name");

        return playlistService.createPlaylist(name);
    }

    /**
     * Creates an endpoint that shows a playlist with the specified id.
     *
     * @return the playlist as a json response
     */
    @GetMapping("/playlists/{id}")
    public Playlist show(@PathVariable("id") long id) throws EntityNotFoundException {
        return playlistService.getPlaylist(id);
    }

    /**
     * Creates an endpoint that updates an existing playlist with the specified id.
     *
     * @return the updated playlist as a json response
     */
    @PutMapping(value = "/playlists/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist update(@PathVariable("id") long id, @RequestBody MultiValueMap<String, String> body) throws EntityNotFoundException, BadRequestException {
        String name = body.getFirst("name");

        return playlistService.updatePlaylist(id, name);
    }

    /**
     * Creates an endpoint that deletes a playlist with the specified id.
     */
    @DeleteMapping("/playlists/{id}")
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException {
        playlistService.deletePlaylist(id);
    }

    /**
     * Creates an endpoint that adds a track to a playlist.
     *
     * @return the updated playlist as a json response
     */
    @PostMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist addTrackToPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.addTrackToPlaylist(id, trackId);
    }

    /**
     * Creates an endpoint that deletes a track from a playlist.
     *
     * @return the updated playlist as a json response
     */
    @DeleteMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist removeTrackFromPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.removeTrackFromPlaylist(id, trackId);
    }
}
