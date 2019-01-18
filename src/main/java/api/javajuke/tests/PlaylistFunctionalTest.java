package api.javajuke.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlaylistFunctionalTest extends FunctionalTest {

    private static String token;
    private static String playlistId;

    @Test
    public void aTestRegister() {
        given()
                .param("email", "playlist@playlist.nl")
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/register")
                .then()
                .statusCode(201);
    }

    @Test
    public void bTestLogin(){
        given()
                .param("email", "playlist@playlist.nl")
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void cTestGetLoginToken() {
        token = given()
                .param("email", "playlist@playlist.nl")
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/login")
                .jsonPath().getString("token");
    }

    @Test
    public void dTestGetPlaylists() {
        given()
                .header("X-Authorization", token)
                .get("/playlists")
                .then()
                .statusCode(200);
    }

    @Test
    public void eTestCreatePlaylist() {
        playlistId = given()
                .header("X-Authorization", token)
                .param("name", "Test Playlist")
                .post("/playlists")
                .then()
                .statusCode(201)
                .extract()
                .path("id").toString();
    }

    @Test
    public void fTestGetPlaylist() {
        given()
                .header("X-Authorization", token)
                .get("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void gTestUpdatePlaylist() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("name", "Updated Test");

        given()
                .header("X-Authorization", token)
                .formParam("name", "Updated Playlist")
                .put("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void hTestDeletePlaylist() {
        given()
                .header("X-Authorization", token)
                .delete("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void iTestDeleteUser() {
        given()
                .header("X-Authorization", token)
                .delete("/users")
                .then()
                .statusCode(200);
    }
}
