package api.stenden.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public long getDuration() { return duration; }

    public void setDuration(long duration) { this.duration = duration; }

    public String getArtist() { return artist; }

    public void setArtist(String artist) { this.artist = artist; }

    public String getAlbum() { return album; }

    public void setAlbum(String album) { this.album = album; }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }
}
