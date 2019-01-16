package api.javajuke.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Nullable
    @Column(name = "cover_path")
    private String coverPath;

    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.PERSIST
    )
    private List<Track> tracks;

    public Album(String name, String coverPath) {
        this.name = name;
        this.coverPath = coverPath;
    }

    public Album() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
}
