package da2.webapp.demo.controllers;

import da2.webapp.demo.models.User;
import da2.webapp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return service.getUserById(id);
    }
}
