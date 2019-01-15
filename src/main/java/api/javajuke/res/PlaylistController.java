package api.javajuke.res;

import api.javajuke.data.model.Playlist;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PlaylistController implements VersionController{
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
    public ResponseEntity index() {
        List<Playlist> playlists = playlistService.getPlaylists();
        HashMap<String, List<Playlist>> map = new HashMap<>();
        map.put("data", playlists);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Creates an endpoint that creates a new playlist.
     *
     * @param body the request body containing POST data
     * @param token the X-Authorization header value which contains the user token
     * @return the newly created playlist as a json response
     */
    @PostMapping(value = "/playlists", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist create(@RequestParam Map<String, String> body, @RequestHeader(value = "X-Authorization") String token) {
        String name = body.get("name");

        return playlistService.createPlaylist(name, token);
    }

    /**
     * Creates an endpoint that shows a playlist with the specified id.
     *
     * @param id the playlist id
     * @return the playlist as a json response
     * @throws EntityNotFoundException when the playlist is not found
     */
    @GetMapping("/playlists/{id}")
    public Playlist show(@PathVariable("id") long id, @RequestParam(value = "search", required = false) Optional<String> search) throws EntityNotFoundException {
        if(search.isPresent()){
            return playlistService.getPlaylist(id, search);
        }
        return playlistService.getPlaylist(id);
    }

    /**
     * Creates an endpoint that updates an existing playlist with the specified id.
     *
     * @param id the playlist id
     * @param body the request body containing POST data
     * @return the updated playlist as a json response
     * @throws EntityNotFoundException when the playlist is not found
     * @throws BadRequestException when an invalid request body is sent
     */
    @PutMapping(value = "/playlists/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist update(@PathVariable("id") long id, @RequestBody MultiValueMap<String, String> body) throws EntityNotFoundException, BadRequestException {
        String name = body.getFirst("name");

        return playlistService.updatePlaylist(id, name);
    }

    /**
     * Creates an endpoint that deletes a playlist with the specified id.
     *
     * @param token the X-Authorization header value which contains the user token
     * @param id the playlist id
     * @throws EntityNotFoundException when the playlist is not found
     */
    @DeleteMapping("/playlists/{id}")
    public void delete(@RequestHeader(value = "X-Authorization") String token, @PathVariable("id") long id) throws EntityNotFoundException{
        playlistService.deletePlaylist(id, token);
    }

    /**
     * Creates an endpoint that adds a track to a playlist.
     *
     * @param id the playlist id
     * @param trackId the track id
     * @return the updated playlist as a json response
     */
    @PostMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist addTrackToPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.addTrackToPlaylist(id, trackId);
    }

    /**
     * Creates an endpoint that deletes a track from a playlist.
     *
     * @param id the playlist id
     * @param trackId the track id
     * @return the updated playlist as a json response
     */
    @DeleteMapping(value = "/playlists/{id}/tracks/{trackId}")
    public Playlist removeTrackFromPlaylist(@PathVariable long id, @PathVariable long trackId) {
        return playlistService.removeTrackFromPlaylist(id, trackId);
    }
}
