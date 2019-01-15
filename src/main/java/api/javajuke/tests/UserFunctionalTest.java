package api.javajuke.tests;

import static org.hamcrest.Matchers.greaterThan;

import org.junit.Test;


import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

<<<<<<< Updated upstream:src/main/java/api/javajuke/tests/UserFunctionalTest.java

public class UserFunctionalTest extends FunctionalTest{
=======
@FixMethodOrder(MethodSorters.DEFAULT)
public class RestEndpointTests extends FunctionalTest{
>>>>>>> Stashed changes:src/main/java/api/javajuke/tests/RestEndpointTests.java

    private String token;

    //User control

    @Test
    public void testRegister() {
        given()
                .param("email", "123")
                .param("username", "123")
                .param("password", "123")
                .post("/register")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogin(){
        given()
                .param("email", "123")
                .param("username", "123")
                .param("password", "123")
                .post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetLoginToken()
    {
        token = given()
                .param("email", "123")
                .param("username", "123")
                .param("password", "123")
                .post("/login")
                .jsonPath()
                .getString("token");
    }

    //Playlists
    @Test
    public void checkPlaylistById() {
        given().header("X-Authorization", token).when().get("/playlists/1").then()
                .body("name",equalTo("Playlist 1"));
    }

    @Test
    public void checkPlaylistNotEmpty() {
        given().header("X-Authorization", token)
                .when().get("/playlists").then()
                .body("$.size()", greaterThan(0));
    }

    //Tracks
    @Test
    public void checkTrackById() {
        given().header("X-Authorization", token)
                .when().get("/tracks/1").then()
                .body("path",equalTo("example/path/1"));
    }

    @Test
    public void checkTracksNotEmpty() {
        given().header("X-Authorization", token)
                .when().get("/tracks").then()
                .body("$.size()", greaterThan(0));
    }
}