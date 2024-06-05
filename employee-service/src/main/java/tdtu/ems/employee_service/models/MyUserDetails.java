package tdtu.ems.employee_service.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {
    private int id;
    private String email;
    private String name;
    private String password;
    private int role;
    private String roleDetail;
    private int departmentId;
    private String departmentName;

    public MyUserDetails(int id, String email, String name, String password, int role, String roleDetail, int departmentId, String departmentName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.roleDetail = roleDetail;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public MyUserDetails(Employee e) {
        this.id = e.getId();
        this.email = e.getEmail();
        this.name = e.getName();
        this.password = e.getPassword();
        this.role = e.getRole();
        this.roleDetail = e.getRoleDetail();
        this.departmentId = e.getDepartmentId();
        this.departmentName = e.getDepartmentName();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getRole() {
        return role;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
