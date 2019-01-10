package api.javajuke.tests;

import static org.hamcrest.Matchers.greaterThan;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestEndpointTests extends FunctionalTest{

    //User control
    @Test
    public void checkLogin(){
        given()
                .param("email", "123")
                .param("username", "123")
                .param("password", "123")
                .post("/login")
                .then()
                .statusCode(200);
    }

    //Playlists
    @Test
    public void checkPlaylistById() {
        given().when().get("/playlists/1").then()
                .body("name",equalTo("Playlist 1"));
    }

    @Test
    public void checkPlaylistNotEmpty() {
        given().when().get("/playlists").then()
                .body("$.size()", greaterThan(0));
    }

    //Tracks
    @Test
    public void checkTrackById() {
        given().when().get("/tracks/1").then()
                .body("path",equalTo("example/path/1"));
    }

    @Test
    public void checkTracksNotEmpty() {
        given().when().get("/tracks").then()
                .body("$.size()", greaterThan(0));
    }
}