package api.javajuke.service;

import api.javajuke.data.UserRepository;
import api.javajuke.data.model.User;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User validateUser(String token) {
        return userRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("User with token " + token + " not found." ));
    }
}
