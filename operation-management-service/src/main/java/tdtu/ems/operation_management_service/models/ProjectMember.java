package tdtu.ems.operation_management_service.models;

import java.util.Date;

public class ProjectMember {
    private int id;
    private int projectId;
    private int employeeId;
    private Date joinDate;
    private int role;   //ProjectRole

    public ProjectMember() {}

    public ProjectMember(int id, int projectId, int employeeId, Date joinDate, int role) {
        this.id = id;
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.joinDate = joinDate;
        this.role = role;
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
