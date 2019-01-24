package api.javajuke.data;

import api.javajuke.data.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    /**
     * Queries for a track with the specified name and artist.
     *
     * @param name the name to search for
     * @param artist the artist to search for
     * @return an optional track object that contains an track if found
     */
    Optional<Track> findByTitleAndArtist(String name, String artist);

    /**
     * Queries for a multiple tracks which contains the given artist, title or album.
     *
     * @param artist the artist to search for
     * @param title the title to search for
     * @param album the album to search for
     * @return list with track objects which meet the search requirements
     */
    List<Track> findAllByArtistContainingOrTitleContainingOrAlbumNameContaining(String artist, String title, String album);

    /**
     * Queries for multiple tracks with the specified album ID.
     *
     * @param albumId the album ID to search for
     * @return list with track objects which have the specified album ID
     */
    List<Track> findByAlbum_Id(long albumId);
}