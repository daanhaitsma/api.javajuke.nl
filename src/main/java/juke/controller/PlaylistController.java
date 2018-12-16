package juke.controller;

import juke.entity.Playlist;
import juke.entity.Track;
import juke.repository.PlaylistRepository;
import juke.repository.TrackRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class PlaylistController {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public PlaylistController(PlaylistRepository playlistRepository, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    @GetMapping("/api/playlists")
    public List<Playlist> index(){
        return playlistRepository.findAll();
    }

    @GetMapping("api/playlists/{id}")
    public Playlist show(@PathVariable String id){
        long playlistId = Long.parseLong(id);
        return playlistRepository.findOne(playlistId);
    }

    @RequestMapping(value = "api/playlists/{id}/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist addSong(@PathVariable String id, @RequestParam Map<String, String> body) throws Exception {
        if (body.get("track_id") == null || !StringUtils.isNumeric(body.get("track_id"))) {
            throw new IllegalArgumentException();
        }

        long track_id = Long.parseLong(body.get("track_id"));
        long playlist_id = Long.parseLong(id);
        if (!trackRepository.exists(track_id) || !playlistRepository.exists(playlist_id)) {
            throw new IllegalArgumentException();
        }

        Track track = trackRepository.getOne(track_id);
        Playlist playlist = playlistRepository.getOne(playlist_id);

        if (playlist.getTracks().contains(track)) {
            throw new EntityExistsException();
        }

        playlist.getTracks().add(track);

        return playlistRepository.save(playlist);
    }
}
