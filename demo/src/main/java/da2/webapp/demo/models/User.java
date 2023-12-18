package da2.webapp.demo.models;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    public int id;
    public String email;
    public String password;
    public String username;
    public String role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
