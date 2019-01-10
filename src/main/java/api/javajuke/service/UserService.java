package api.javajuke.service;

import api.javajuke.data.UserRepository;
import api.javajuke.data.model.User;
import api.javajuke.exception.EntityNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for the UserService class.
     *
     * @param userRepository user repository containing all user data
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Logs in a user with the specified email or username and password. First finds a user
     * with the email or username and then checks if the password matches.
     *
     * @param email    the users email
     * @param username the users username
     * @param password the users password
     * @return the logged in users token
     */
    public String loginUser(String email, String username, String password){
        User user = userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(() -> new EntityNotFoundException("Login credentials are incorrect."));

        if (BCrypt.checkpw(password, user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);
            return user.getToken();
        }else{
            throw new EntityNotFoundException("Login credentials are incorrect.");
        }
    }

    /**
     * Creates a new user with the specified username, email and password.
     *
     * @param email    the email for the new user
     * @param username the username for the new user
     * @param password the password for the new user
     * @return the newly created user
     */
    public User createUser(String email, String username, String password){
        if(!userRepository.findByUsernameOrEmail(username, email).isPresent()) {
            User newUser = new User(email, username, password);

            userRepository.save(newUser);
            return newUser;
        }
        throw new EntityNotFoundException("There's already a user with the same username or email");
    }

    /**
     * Logs the currently logged in user out with the specified token.
     * 
     * @param token the token of the user that wants to log out
     */
    public void logoutUser(String token){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));

        user.setToken("");
        userRepository.save(user);
    }
}
