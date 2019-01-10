package api.javajuke.res;

import api.javajuke.data.model.User;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    /**
     * Constructor for the UserController class.
     *
     * @param userService the user service containing all user logic
     */
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Creates an API endpoint to create login with the specified email or username and password.
     *
     * @param body the body containing the POST data with which the user can be logged in
     * @return the token for the logged in user
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@RequestBody MultiValueMap<String, String> body){
        String email = body.getFirst("email");
        String username = body.getFirst("username");
        String password = body.getFirst("password");

        return userService.loginUser(email, username, password);
    }

    /**
     * Creates an API endpoint to register a new user.
     *
     * @param body the body containing the POST data to create a new user with
     * @return the newly created user
     * @throws EntityNotFoundException when the user already exists
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User register(@RequestBody MultiValueMap<String, String> body) throws EntityNotFoundException {
        String email = body.getFirst("email");
        String username = body.getFirst("username");
        String password = body.getFirst("password");

        return userService.createUser(email, username, password);
    }

    /**
     * Creates an API endpoint to log a logged in user out
     *
     * @param token the token of the user that wants to log out
     */
    @GetMapping(value = "/logout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void logout(@RequestHeader(value = "X-Authorization") String token){
        userService.logoutUser(token);
    }
}
