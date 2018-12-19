package api.stenden.service;

import api.stenden.data.PlaylistRepository;
import api.stenden.data.model.Playlist;
import api.stenden.data.model.Track;
import api.stenden.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylist(long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist with ID " + id + " not found." ));
    }
}
