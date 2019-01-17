package api.javajuke.tests;

import api.javajuke.data.model.Track;
import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrackFunctionalTest extends FunctionalTest {

    private static String token;
    private static int trackId;
    private static String info;
    List<Entity> list = new ArrayList<>();

    @Test
    public void aTestRegister() {
        given()
                .param("email", "track@track.nl")
                .param("username", "track")
                .param("password", "track")
                .post("/register")
                .then()
                .statusCode(200);
    }

    @Test
    public void bTestLogin(){
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
        token = given()
                .param("email", "track@track.nl")
                .param("username", "track")
                .param("password", "track")
                .post("/login")
                .jsonPath().getString("token");
    }

    @Test
    public void dTestGetTracks() {
        given()
                .header("X-Authorization", token)
                .get("/tracks")
                .then()
                .statusCode(200);
    }

    @Test
    public void eTestUploadTrack() throws IOException {
        File file = new ClassPathResource("testsound.mp3").getFile();

        given()
                .header("X-Authorization", token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("files", file)
                .post("/tracks")
                .then()
                .statusCode(200);
    }

    @Test
    public void fgetTrackNameForTesting() {

        Response response = given()
                .header("X-Authorization", token)
                .when()
                .get("/tracks");

        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        JSONArray values = JSONResponseBody.getJSONArray("data");

        for (int i = 0; i < values.length(); i++) {

            JSONObject value = values.getJSONObject(i);
            String name = value.getString("title");

            if(name.equals("Niels")) {
                trackId = value.getInt("id");
            }
        }
    }

    @Test
    public void gTestGetTrack() {
        given()
                .header("X-Authorization", token)
                .get("/tracks/" + trackId)
                .then()
                .statusCode(200);
    }


    @Test
    public void hTestDeleteTrack() {
        given()
                .header("X-Authorization", token)
                .delete("/tracks/" + trackId)
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
