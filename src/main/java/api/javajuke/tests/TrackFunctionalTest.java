package api.javajuke.tests;

import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrackFunctionalTest extends FunctionalTest {

    private static String token;
    private static int trackId;

    @Test
    public void aTestRegister() {
        // Try registering with given username and password,
        // when statuscode equals 201 the process is executed succesful
        given()
                .param("username", "track")
                .param("password", "track")
                .post("/register")
                .then()
                .statusCode(201);
    }

    @Test
    public void bTestLogin(){
        // Try login with the given username and password
        // When statuscode equals 200 the process is executed sucesful
        given()
                .param("email", "track@track.nl")
                .param("username", "track")
                .param("password", "track")
                .post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void cTestGetLoginToken() {
        // Try to obtain the login token of the logged in account
        token = given()
                .param("email", "track@track.nl")
                .param("username", "track")
                .param("password", "track")
                .post("/login")
                .jsonPath().getString("token");
    }

    @Test
    public void dTestGetTracks() {
        // Try to get all tracks, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .get("/tracks")
                .then()
                .statusCode(200);
    }

    @Test
    public void eTestUploadTrack() throws IOException {
        // Try to upload a track, when statuscode equals 201
        // the process is executed succesful

        // Get track from resource path
        File file = new ClassPathResource("5 - Three Days Grace - Nothing To Lose But You.mp3").getFile();

        given()
                .header("X-Authorization", token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("files", file)
                .post("/tracks")
                .then()
                .statusCode(201);
    }

    @Test
    public void fgetTrackNameForTesting() {
        // Obtain the trackname and id for the following tests.
        Response response = given()
                .header("X-Authorization", token)
                .when()
                .get("/tracks");

        // Extract JSON response by key data
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        JSONArray values = JSONResponseBody.getJSONArray("data");

        // Obtain the id by trackname
        for (int i = 0; i < values.length(); i++) {

            JSONObject value = values.getJSONObject(i);
            String name = value.getString("title");

            if(name.equals("Nothing To Lose But You")) {
                trackId = value.getInt("id");
            }
        }
    }

    @Test
    public void gTestGetTrack() {
        // Try to get a track, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .get("/tracks/" + trackId)
                .then()
                .statusCode(200);
    }


    @Test
    public void hTestDeleteTrack() {
        // Try to delete a track, when statuscode equals 200
        // the process is executed succesfull
        given()
                .header("X-Authorization", token)
                .delete("/tracks/" + trackId)
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
