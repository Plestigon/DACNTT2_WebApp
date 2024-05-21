package tdtu.ems.operation_management_service.models;

import java.util.Date;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private int ownerId;
    private List<Integer> memberIds;    //ProjectMember
    private int status;
    private Date dueDate;
    private String description;
    private List<Integer> projectUpdateIds; //ProjectUpdate

    public Project() {}

    public Project(String name, int ownerId, int status, Date dueDate, String description) {
        this.name = name;
        this.ownerId = ownerId;
        this.status = status;
        this.dueDate = dueDate;
        this.description = description;
    }

    public Project(Project p) {
        this.id = p.id;
        this.name = p.name;
        this.ownerId = p.ownerId;
        this.memberIds = p.memberIds;
        this.status = p.status;
        this.dueDate = p.dueDate;
        this.description = p.description;
        this.projectUpdateIds = p.projectUpdateIds;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getProjectUpdateIds() {
        return projectUpdateIds;
    }

    public void setProjectUpdateIds(List<Integer> projectUpdateIds) {
        this.projectUpdateIds = projectUpdateIds;
    }
}
