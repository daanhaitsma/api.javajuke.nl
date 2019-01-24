package api.javajuke.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @JsonManagedReference
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "playlist_track",
            joinColumns = {
                @JoinColumn(name = "playlist_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "track_id")
            })
    @OrderBy("id ASC")
    private Set<Track> tracks = new HashSet<>();

    @JsonIgnoreProperties({"token", "email"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Playlist() {}

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
