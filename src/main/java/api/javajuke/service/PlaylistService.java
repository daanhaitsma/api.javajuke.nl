package api.javajuke.service;

import api.javajuke.data.PlaylistRepository;
import api.javajuke.data.UserRepository;
import api.javajuke.data.model.Playlist;
import api.javajuke.data.model.Track;
import api.javajuke.data.model.User;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TrackService trackService;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, TrackService trackService, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.trackService = trackService;
        this.userRepository = userRepository;
    }

    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(String name, String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));

        Playlist playlist = new Playlist(name, user);

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

        // Iterate over all the tracks in the playlist
        for (Track playlistTrack : playlist.getTracks()) {
            // If the track to be added is already found, don't add it again
            if (playlistTrack.getId() == trackId) {
                // Stop when the track is found, so as to not keep iterating unnecessarily
                throw new BadRequestException("Track already exists in this playlist");
            }
        }

        playlist.getTracks().add(track);
        return playlistRepository.save(playlist);

    }

    public Playlist removeTrackFromPlaylist(long id, long trackId) {
        Playlist playlist = getPlaylist(id);

        Iterator<Track> trackIterator = playlist.getTracks().iterator();
        // Iterate over all the tracks
        while (trackIterator.hasNext()) {
            Track track = trackIterator.next();
            // If the track to be deleted is found, actually delete it
            if (track.getId() == trackId) {
                trackIterator.remove();
                // Stop when the track is found, so as to not keep iterating unnecessarily
                return playlistRepository.save(playlist);
            }
        }

        throw new BadRequestException("Track doesn't exists in this playlist");
    }
}
