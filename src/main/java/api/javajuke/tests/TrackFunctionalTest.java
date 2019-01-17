package api.javajuke.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrackFunctionalTest extends FunctionalTest {

    private static String token;
    private static String trackId;

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

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        // read as a file
        File file = new File(classloader.getResource("test.mp3").getFile());

        trackId = given()
                .header("X-Authorization", token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", file)
                .post("/tracks")
                .then()
                .statusCode(200)
                .extract()
                .path("id").toString();
    }

    @Test
    public void fTestGetTrack() {
        given()
                .header("X-Authorization", token)
                .get("/tracks/" + trackId)
                .then()
                .statusCode(200);
    }


    @Test
    public void gTestDeleteTrack() {
        given()
                .header("X-Authorization", token)
                .delete("/tracks/" + trackId)
                .then()
                .statusCode(200);
    }

    @Test
    public void hTestDeleteUser() {
        given()
                .header("X-Authorization", token)
                .delete("/users")
                .then()
                .statusCode(200);
    }
}
