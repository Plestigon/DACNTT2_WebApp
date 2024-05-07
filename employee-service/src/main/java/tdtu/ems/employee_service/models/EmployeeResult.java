package tdtu.ems.employee_service.models;

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

    public EmployeeResult() {
    }

    public EmployeeResult(Employee e) {
        this.id = e.getId();
        this.email = e.getEmail();
        this.name = e.getName();
        this.role = e.getRole();
        this.roleDetail = e.getRoleDetail();
        this.departmentId = e.getDepartmentId();
        this.departmentName = e.getDepartmentName();
        this.joinDate = e.getJoinDate();
        this.leaveDate = e.getLeaveDate();
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
