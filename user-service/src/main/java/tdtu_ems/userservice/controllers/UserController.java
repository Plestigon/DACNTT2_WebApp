package tdtu_ems.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        List<User> users = null;
        users = userService.getUsers();
        if (users == null || users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users;
    }

    @GetMapping("/user/id/{id}")
    public User getUserById(@PathVariable int id) throws ExecutionException, InterruptedException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }


    @GetMapping("/users/department/{shortName}")
    public List<User> getUsersByDepartment(@PathVariable String shortName) throws ExecutionException, InterruptedException {
        List<User> response = userService.getUsersByDepartment(shortName);
        if (response == null || response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/user/email/{email}")
    public User getUserByEmail(@PathVariable String email) throws ExecutionException, InterruptedException {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userService.addUser(user);
    }

    @PostMapping("/remove-user")
    public String removeUser(@RequestParam int id) throws ExecutionException, InterruptedException {
        return userService.removeUser(id);
    }
}
