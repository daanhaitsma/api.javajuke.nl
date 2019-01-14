package api.javajuke.res;

import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.MediaplayerService;
import api.javajuke.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, MediaplayerService mediaplayerService)
    {
        this.playlistService = playlistService;
        this.mediaplayerService = mediaplayerService;
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

    @PutMapping(value = "/playlists/{id}/play")
    public void play(@PathVariable("id") long id){
        Playlist playlist = playlistService.getPlaylist(id);
        mediaplayerService.playPlaylist(playlist);
    }

    private final MediaplayerService mediaplayerService;

}
