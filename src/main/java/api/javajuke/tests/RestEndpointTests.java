package api.javajuke.tests;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import static org.hamcrest.Matchers.greaterThan;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestEndpointTests extends FunctionalTest{

    @Test
    public void basicPingTest() {
        given().when().get("/playlists").then().statusCode(200);
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