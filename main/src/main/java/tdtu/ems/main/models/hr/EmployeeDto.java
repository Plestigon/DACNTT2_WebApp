package tdtu.ems.main.models.hr;

public class EmployeeDto {
    private String email;
    private String password;
    private String name;
    private int role;
    private String roleDetail;
    private int departmentId;
    private String departmentName;

    public EmployeeDto() {}

    public EmployeeDto(String email, String password, String name, int role, String roleDetail, int departmentId, String departmentName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.roleDetail = roleDetail;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
