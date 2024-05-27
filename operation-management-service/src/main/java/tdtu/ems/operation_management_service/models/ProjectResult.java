package tdtu.ems.operation_management_service.models;

import tdtu.ems.core_service.models.Enums;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProjectResult {
    private int id;
    private String name;
    private int ownerId;
    private String ownerName;
    private List<Integer> memberIds;
    private int status;
    private String statusName;
    private Date createDate;
    private Date dueDate;
    private String description;
    private List<Integer> projectUpdateIds;

    public ProjectResult(int id, String name, int ownerId, String ownerName, List<Integer> memberIds, int status, String statusName, Date createDate, Date dueDate, String description, List<Integer> projectUpdateIds) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.memberIds = memberIds;
        this.status = status;
        this.statusName = statusName;
        this.createDate = createDate;
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
        this.status = p.getStatus();
        this.statusName = Enums.ProjectStatus.values()[p.getStatus()].name;
        this.createDate = p.getCreateDate();
        this.dueDate = p.getDueDate();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
