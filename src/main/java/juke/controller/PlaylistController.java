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
    public Playlist addTrack(@PathVariable String id, @RequestParam Map<String, String> body) {
        if (body.get("track_id") == null || !StringUtils.isNumeric(body.get("track_id"))) {
            throw new IllegalArgumentException();
        }

        long trackId = Long.parseLong(body.get("track_id"));
        long playlistId = Long.parseLong(id);
        if (!trackRepository.exists(trackId) || !playlistRepository.exists(playlistId)) {
            throw new IllegalArgumentException();
        }

        Track track = trackRepository.getOne(trackId);
        Playlist playlist = playlistRepository.getOne(playlistId);

        if (playlist.getTracks().contains(track)) {
            throw new EntityExistsException();
        }

        playlist.getTracks().add(track);

        return playlistRepository.save(playlist);
    }

    @RequestMapping(value = "api/playlists/{id}/remove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Playlist removeTrack(@PathVariable String id, @RequestParam Map<String, String> body) {
        if (body.get("track_id") == null || !StringUtils.isNumeric(body.get("track_id"))) {
            throw new IllegalArgumentException();
        }

        long trackId = Long.parseLong(body.get("track_id"));
        long playlistId = Long.parseLong(id);
        if (!trackRepository.exists(trackId) || !playlistRepository.exists(playlistId)) {
            throw new IllegalArgumentException();
        }

        Track track = trackRepository.getOne(trackId);
        Playlist playlist = playlistRepository.getOne(playlistId);

        if (!playlist.getTracks().contains(track)) {
            throw new EntityExistsException();
        }

        playlist.getTracks().remove(track);

        return playlistRepository.save(playlist);
    }
}
