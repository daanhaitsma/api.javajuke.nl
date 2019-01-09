package api.javajuke.service;

import api.javajuke.data.UserRepository;
import api.javajuke.data.model.User;
import api.javajuke.exception.EntityNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String loginUser(String email, String username, String password){
        User user = userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));

        if (BCrypt.checkpw(password, user.getPassword())){
            return user.getToken();
        }else{
            return null;
        }
    }

    public User createUser(String email, String username, String password){
        User user = new User(email, username, password);
        userRepository.save(user);
        return user;
    }
}
