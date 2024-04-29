package tdtu.ems.operation_management_service.models;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjectResult {
    private int id;
    private String name;
    private int ownerId;
    private String ownerName;
    private List<Integer> memberIds;
    private String status;
    private String dueDate;
    private String description;
    private List<Integer> projectUpdateIds;

    public ProjectResult(int id, String name, int ownerId, String ownerName, List<Integer> memberIds, String status, String dueDate, String description, List<Integer> projectUpdateIds) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.memberIds = memberIds;
        this.status = status;
        this.dueDate = dueDate;
        this.description = description;
        this.projectUpdateIds = projectUpdateIds;
    }

    public ProjectResult(Project p, String ownerName) {
        this.id = p.getId();
        this.name = p.getName();
        this.ownerId = p.getOwnerId();
        this.ownerName = ownerName;
        this.memberIds = p.getMemberIds();
        this.status = p.getStatus() == 1 ? "open" : "closed";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.dueDate = formatter.format(p.getDueDate());
        this.description = p.getDescription();
        this.projectUpdateIds = p.getProjectUpdateIds();
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
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
