package api.javajuke.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserFunctionalTest extends FunctionalTest{

    private static String token;

    @Test
    public void aTestRegister() {
        given()
                .param("email", "playlist@playlist.nl")
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/register")
                .then()
                .statusCode(200);
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
    public void dTestDeleteUser() {
        given()
                .header("X-Authorization", token)
                .delete("/users")
                .then()
                .statusCode(200);
    }
}