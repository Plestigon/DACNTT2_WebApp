package tdtu_ems.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu_ems.userservice.models.User;
import tdtu_ems.userservice.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public List<User> addUsers(List<User> users) {
        return repository.saveAll(users);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
}
