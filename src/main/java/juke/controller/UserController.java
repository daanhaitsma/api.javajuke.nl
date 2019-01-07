package juke.controller;

import juke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import juke.entity.User;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController

public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository UserRepository) {
        this.userRepository = UserRepository;
    }

    //Get all users
    @GetMapping("/api/users")
    public List<User> index(){
        return userRepository.findAll();
    }

    //Register user
    @RequestMapping(value = "api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User create(@RequestParam Map<String, String> body){
        if (body.get("first_name")  == null || body.get("last_name")  == null || body.get("email")  == null || body.get("password")  == null) {
            throw new IllegalArgumentException();
        }
        User user = new User(body.get("first_name"), body.get("last_name"), body.get("email"), body.get("password"));

        return userRepository.save(user);
    }

    //Get 1 user
    @GetMapping("api/users/{id}")
    public User get(@PathVariable String id){
        long userID = Long.parseLong(id);
        return userRepository.findOne(userID);
    }

    //Update user
    @RequestMapping(value = "api/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User update(@PathVariable String id, @RequestParam Map<String, String> body){
        if (body.get("first_name") == null || body.get("last_name") == null || body.get("email") == null || body.get("password") == null) {
            throw new IllegalArgumentException();
        }

        long userId = Long.parseLong(id);

        User user = userRepository.findOne(userId);
        user.setFirstName(body.get("first_name"));
        user.setLastName(body.get("last_name"));
        user.setFirstName(body.get("email"));
        user.setLastName(body.get("password"));

        return userRepository.save(user);
    }

    //Delete user by userID
    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void delete(@PathVariable String id){
        long userId = Long.parseLong(id);
        if (!userRepository.exists(userId)) {
            throw new IllegalArgumentException();
        }

        userRepository.delete(userId);
    }

    //Check if user exists
    @GetMapping("api/users/{email}")
    public User checkUser(@PathVariable String first_name, String last_name, String email, @RequestParam Map<String, String> body){
        if (body.get("first_name") == null || body.get("last_name") == null || body.get("email") == null) {
            throw new IllegalArgumentException();
        }

        String user_first_name = first_name;
        String user_last_name = last_name;
        String user_email = email;

        //Check als email bestaat

        //verwacht long userID moet email zijn..
        return userRepository.findOne();
    }
}


