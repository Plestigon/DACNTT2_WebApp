package tdtu.ems.operation_management_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;

public class ProjectMemberResult {
    private int id;
    private int projectId;
    private int employeeId;
    private String employeeName;
    private String employeeEmail;
    private Date joinDate;
    private int role;   //ProjectRole

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;

    public ProjectMemberResult(ProjectMember p, String employeeName, String employeeEmail) {
        this.id = p.getId();
        this.projectId = p.getProjectId();
        this.employeeId = p.getEmployeeId();
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.joinDate = p.getJoinDate();
        this.role = p.getRole();
        this.roleName = Enums.ProjectRole.values()[p.getRole()].name();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
