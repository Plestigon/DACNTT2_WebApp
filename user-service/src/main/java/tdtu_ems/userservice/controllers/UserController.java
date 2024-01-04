package tdtu_ems.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tdtu_ems.userservice.models.User;
import tdtu_ems.userservice.services.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = null;
        try {
            users = userService.getUsers();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

//    @GetMapping("/user/{id}")
//    public User getUserById(@PathVariable int id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/user")
//    public User getUserByEmail(@RequestParam String email) {
//        return userService.getUserByEmail(email);
//    }
//
//    @PostMapping("/add-user")
//    public User addUser(@RequestBody User user) {
//        return userService.addUser(user);
//    }
}
