package api.javajuke.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String path;
    @Nullable
    private String title;
    @Nullable
    private String artist;
    private long duration;
    @Nullable
    private String album;

    @JsonBackReference
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tracks")
    private Set<Playlist> playlists = new HashSet<>();

    public Track() {}

    public Track(String path) {
        this.path = path;
    }

    public File getFile() {
        return new File(getPath());
    }
}
