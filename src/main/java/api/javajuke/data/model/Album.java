package api.javajuke.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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
    @JsonBackReference
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
}
