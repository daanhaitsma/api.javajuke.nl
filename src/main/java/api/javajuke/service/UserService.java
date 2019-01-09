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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public User createUser(String email, String username, String password){
        if(userRepository.findByUsernameOrEmail(username, email).isEmpty()) {
            User newUser = new User(email, username, password);

            userRepository.save(newUser);
            return newUser;
        }
        throw new EntityNotFoundException("There's already a user with the same username or email");
    }

    public void logoutUser(String token){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));

        user.setToken("");
        userRepository.save(user);
    }
}
