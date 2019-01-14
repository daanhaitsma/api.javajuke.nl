package api.javajuke.tests;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class PlaylistFunctionalTest extends FunctionalTest {

    String token = "961b48c2-80dd-4205-8ebf-ea56aecf6d1d";
    String playlistId;

    @Test
    public void testGetPlaylists() {
        given()
                .header("X-Authorization", token)
                .get("/playlists")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreatePlaylist() {
        playlistId = given()
                .header("X-Authorization", token)
                .param("name", "test")
                .post("/playlists")
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Test
    public void testGetPlaylist() {
        given()
                .header("X-Authorization", token)
                .get("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdatePlaylist() {
        given()
                .header("X-Authorization", token)
                .param("name", "updatedTest")
                .put("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeletePlaylist() {
        given()
                .header("X-Authorization", token)
                .delete("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

}
