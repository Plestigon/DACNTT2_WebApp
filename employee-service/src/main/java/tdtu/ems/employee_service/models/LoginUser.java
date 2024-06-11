package tdtu.ems.employee_service.models;

public class LoginUser {
    private int id;
    private String name;
    private String email;
    private int role;
    private String roleName;
    private int department;
    private String departmentName;

    public LoginUser() {}

    public LoginUser(Employee o) {
        this.id = o.getId();
        this.name = o.getName();
        this.email = o.getEmail();
        this.role = o.getRole();
        this.roleName = o.getRoleDetail();
        this.department = o.getDepartmentId();
        this.departmentName = o.getDepartmentName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
