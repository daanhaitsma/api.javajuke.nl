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
        // Try registering with given username and password,
        // when statuscode equals 201 the process is executed succesful
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
        // Try login with the given username and password
        // When statuscode equals 200 the process is executed sucesful
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
        // Try to obtain the login token of the logged in account
        token = given()
                .param("email", "playlist@playlist.nl")
                .param("username", "playlist")
                .param("password", "playlist")
                .post("/login")
                .jsonPath().getString("token");
    }

    @Test
    public void dTestDeleteUser() {
        // Try to delete the created user, when statuscode equals 200
        // the process is executed succesful
        given()
                .header("X-Authorization", token)
                .delete("/users")
                .then()
                .statusCode(200);
    }
}