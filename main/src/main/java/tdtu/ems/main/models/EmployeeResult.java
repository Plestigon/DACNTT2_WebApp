package tdtu.ems.main.models;

import java.util.Date;

public class EmployeeResult {
    private int id;
    private String email;
    private String name;
    private int role;
    private String roleDetail;
    private int departmentId;
    private String departmentName;
    private Date joinDate;
    private Date leaveDate;

    public EmployeeResult() {}

    public EmployeeResult(int id, String email, String name, int role, int departmentId, String departmentName, Date joinDate, Date leaveDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
}
