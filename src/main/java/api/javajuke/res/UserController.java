package api.javajuke.res;

import api.javajuke.data.model.User;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.UserService;
import org.graalvm.compiler.nodes.memory.MemoryCheckpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PutMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@RequestBody MultiValueMap<String, String> body){
        String email = body.getFirst("email");
        String username = body.getFirst("username");
        String password = body.getFirst("password");

        return userService.loginUser(email, username, password);
    }

    @PutMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User register(@RequestBody MultiValueMap<String, String> body) throws EntityNotFoundException, BadRequestException {
        String email = body.getFirst("email");
        String username = body.getFirst("username");
        String password = body.getFirst("password");

        return userService.createUser(email, username, password);
    }

    @PutMapping(value = "/isauthenticated", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User isAuthenticated(@RequestBody MultiValueMap<String, String> body){
        String token = body.getFirst("token");

        return userService.getUserByToken(token);
    }
}
