package api.javajuke.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import jdk.internal.jline.internal.Nullable;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String username;
    private String password;
    @Nullable
    private String token;
    @JsonBackReference
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<Playlist> playlists = new HashSet<>();

    public User(){ }

    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.token = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }
}
