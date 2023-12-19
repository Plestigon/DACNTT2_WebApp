package da2.webapp.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String email;
    public String password;
    public String name;
    public String role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
