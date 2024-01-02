package tdtu_ems.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "short_name")
    private String shortName;
    private String name;
    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<User> users;
    @PreRemove
    private void preRemove() {
        users.forEach(user -> user.setDepartment(null));
    }

    public Department(){}

    public Department(int id, String shortName, String name, List<User> users) {
        this.id = id;
        this.shortName = shortName;
        this.name = name;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
