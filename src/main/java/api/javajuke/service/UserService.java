package api.javajuke.service;

import api.javajuke.data.UserRepository;
import api.javajuke.data.model.User;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String email, String username, String password){
        return userRepository.findByUsernameOrEmailAndPassword(username, email, password)
                .orElseThrow(() -> new EntityNotFoundException("Login credentials aren't correct"));
    }

    public User createUser(String email, String username, String password){
        User user = new User(email, username, password);
        userRepository.save(user);
        return user;
    }

    public User getUserByToken(String token){
        return userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("User with token " + token + " not found." ));
    }
}
