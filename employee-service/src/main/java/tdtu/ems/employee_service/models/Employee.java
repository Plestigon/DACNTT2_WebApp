package tdtu.ems.employee_service.models;

import java.util.Date;

public class Employee {
    private int id;
    private String email;
    private String password;
    private String name;
    private int role;
    private String roleDetail;
    private int departmentId;
    private String departmentName;
    private Date joinDate;
    private Date leaveDate;

    public Employee() {}

    public Employee(String email, String password, String name, int role, String roleDetail, int departmentId, String departmentName, Date joinDate, Date leaveDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.roleDetail = roleDetail;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.joinDate = joinDate;
        this.leaveDate = leaveDate;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
