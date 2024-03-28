package tdtu.ems.main.models;

import java.util.Date;
import java.util.List;

public class ProjectDto {
    private int id;
    private String name;
    private int ownerId;
    private String ownerName;
    private List<Integer> memberIds;
    private int status;
    private Date dueDate;
    private String description;
    private List<Integer> projectUpdateIds;

    public ProjectDto() {}

    public ProjectDto(int id, String name, int ownerId, String ownerName, int status, Date dueDate, String description) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.status = status;
        this.dueDate = dueDate;
        this.description = description;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}