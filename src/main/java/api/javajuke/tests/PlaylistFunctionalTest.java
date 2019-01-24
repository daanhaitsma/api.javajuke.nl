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
        // Try registering with given username and password,
        // when statuscode equals 201 the process is executed succesful
        given()
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/register")
                .then()
                .statusCode(201);
    }

    @Test
    public void bTestLogin(){
        // Try login with the given username and password
        // When statuscode equals 200 the process is executed sucesful
        given()
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void cTestGetLoginToken() {
        // Try to obtain the login token of the logged in account
        token = given()
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/login")
                .jsonPath().getString("token");
    }

    @Test
    public void dTestGetPlaylists() {
        //Try to get all playlists, when statuscode equals 200 the process is succesful
        given()
                .header("X-Authorization", token)
                .get("/playlists")
                .then()
                .statusCode(200);
    }

    @Test
    public void eTestCreatePlaylist() {
        // Try to create playlist and obtain the id of this playlist, when statuscode equals
        // 201 the proces is executed succesful
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
        // Try to get the created playlist, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .get("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void gTestUpdatePlaylist() {
        // Try to update a playlist, when statuscode equals 200
        // the process is executed succesful
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
        // Try to delete a playlist, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .delete("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void iTestDeleteUser() {
        // Try to delete the created user, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .delete("/users")
                .then()
                .statusCode(200);
    }
}
