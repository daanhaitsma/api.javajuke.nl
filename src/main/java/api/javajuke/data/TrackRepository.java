package api.javajuke.data;

import api.javajuke.data.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByTitleAndArtist(String name, String artist);
    Optional<List<Track>> findAllByArtistContainingOrTitleContainingOrAlbumContaining(String artist, String title, String album);
}