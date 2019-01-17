package api.javajuke.data;

import api.javajuke.data.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByCoverPath(String coverPath);
}