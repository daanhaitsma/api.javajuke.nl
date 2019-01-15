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
import java.util.*;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TrackService trackService;
    private final UserRepository userRepository;

    /**
     * Constructor for the PlaylistService class.
     *
     * @param playlistRepository playlist repository containing all playlist data
     * @param trackService track service containing track logic
     * @param userRepository user repository containing user logic
     */
    public PlaylistService(PlaylistRepository playlistRepository, TrackService trackService, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.trackService = trackService;
        this.userRepository = userRepository;
    }

    /**
     * Gets all playlists from the playlist repository.
     *
     * @return list with all playlists
     */
    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    /**
     * Creates a new playlist with the specified name and user token
     *
     * @param name the playlist name
     * @param token the owners token
     * @return the newly created playlist
     */
    public Playlist createPlaylist(String name, String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));

        Playlist playlist = new Playlist(name, user);

        return playlistRepository.save(playlist);
    }

    /**
     * Gets a playlist with the specified id.
     *
     * @param id the id of the playlist to get
     * @return the playlist
     */
    public Playlist getPlaylist(long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist with ID " + id + " not found." ));
    }

    /**
     * Gets a playlist with the specified id and filters the playlists
     * tracks based on the specified search query.
     *
     * @param id the playlists id to get
     * @param search the search query to search for
     * @return the playlist with filtered tracks based on the search query
     */
    public Playlist getPlaylist(long id, Optional<String> search){
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist with ID " + id + " not found." ));
        String searchInput = search.get();

        Set<Track> tracks = new HashSet<>();
        for (Track item : playlist.getTracks()) {
            // Check for each track if it matches the search criteria
            if(item.getAlbum().contains(searchInput) || item.getArtist().contains(searchInput) || item.getTitle().contains(searchInput)){
                tracks.add(item);
            }
        }
        // Update the playlist tracks with the filtered tracks
        playlist.setTracks(tracks);
        return playlist;
    }

    /**
     * Updates an existing playlist with the specified id.
     *
     * @param id the id of the playlist to update
     * @param name the new playlist name
     * @return the updated playlist
     */
    public Playlist updatePlaylist(long id, String name) {
        if (name == null || name.equals("")) {
            throw new BadRequestException("Required request part 'name' is missing.");
        }

        Playlist playlist = getPlaylist(id);
        playlist.setName(name);

        return playlistRepository.save(playlist);
    }

    /**
     * Delete an existing playlist with the specified id.
     *
     * @param id the id of the playlist to delete
     * @param token the playlist owners token
     */
    public void deletePlaylist(long id, String token) {
        Playlist playlist = getPlaylist(id);

        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));
        // Check if the user is the playlists owner
        if(playlist.getUser().getId() != user.getId()) {
            throw new BadRequestException("Wrong user");
        }

        Iterator<Playlist> playlistIterator = user.getPlaylists().iterator();
        // Iterate over all the playlists
        while (playlistIterator.hasNext()) {
            Playlist pl = playlistIterator.next();
            // If the playlist to be deleted is found, actually delete it
            if (pl.getId() == id) {
                playlistIterator.remove();
                userRepository.save(user);
                // Stop when the playlist is found, so as to not keep iterating unnecessarily
                return;
            }
        }
    }

    /**
     * Adds a track to a playlist.
     *
     * @param id the playlist id
     * @param trackId the track id
     * @return the playlist containing the added track
     */
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

    /**
     * Removes a track from a playlist.
     *
     * @param id the playlist id
     * @param trackId the track id
     * @return the playlist without the removed track
     */
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
