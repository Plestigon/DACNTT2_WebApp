package tdtu.ems.main.models;

public class EmployeeDto {
    private int id;
    private String email;
    private String name;
    private String role;
    private int departmentId;
    private String departmentShort;
    private String departmentLong;

    public EmployeeDto() {}

    public EmployeeDto(int id, String email, String name, String role, int departmentId, String departmentShort, String departmentLong) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.departmentId = departmentId;
        this.departmentShort = departmentShort;
        this.departmentLong = departmentLong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentShort() {
        return departmentShort;
    }

    public void setDepartmentShort(String departmentShort) {
        this.departmentShort = departmentShort;
    }

    public String getDepartmentLong() {
        return departmentLong;
    }

    public void setDepartmentLong(String departmentLong) {
        this.departmentLong = departmentLong;
    }
}
