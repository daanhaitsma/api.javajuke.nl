package api.javajuke.data;

import api.javajuke.data.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    /**
     * Queries for an album with the specified cover path.
     *
     * @param coverPath the cover path to search for
     * @return an optional album object that contains an album if found
     */
    Optional<Album> findByCoverPath(String coverPath);
}