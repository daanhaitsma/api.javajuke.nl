package api.javajuke.service;

import api.javajuke.data.PlaylistRepository;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TrackService trackService;

    public PlaylistService(PlaylistRepository playlistRepository, TrackService trackService) {
        this.playlistRepository = playlistRepository;
        this.trackService = trackService;
    }

    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);

        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylist(long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist with ID " + id + " not found." ));
    }

    public Playlist updatePlaylist(long id, String name) {
        if (name == null || name.equals("")) {
            throw new BadRequestException("Required request part 'name' is missing.");
        }

        Playlist playlist = getPlaylist(id);
        playlist.setName(name);

        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(long id) {
        Playlist playlist = getPlaylist(id);

        playlistRepository.delete(playlist);
    }

    public Playlist addTrackToPlaylist(long id, long trackId) {
        Track track = trackService.getTrack(trackId);

        Playlist playlist = getPlaylist(id);
        playlist.getTracks().add(track);

        return playlistRepository.save(playlist);
    }

    public Playlist removeTrackFromPlaylist(long id, long trackId) {
        Track track = trackService.getTrack(trackId);

        Playlist playlist = getPlaylist(id);
        playlist.getTracks().remove(track);

        return playlistRepository.save(playlist);
    }
}