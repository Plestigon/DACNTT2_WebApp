package tdtu.ems.operation_management_service.models;

import java.util.Date;
import java.util.List;

public class Task {
    private int id;
    private String name;
    private int projectId;
    private int assigneeId; //EmployeeId
    private Date createDate;
    private Date updateDate;
    private Date dueDate;
    private int priority;
    private int state;  //TaskState
    private String description;
    private List<Integer> discussions;

    public Task() {}

    public Task(int id, String name, int projectId, int assigneeId, Date createDate, Date updateDate, Date dueDate, int priority, int state, String description, List<Integer> discussions) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
        this.assigneeId = assigneeId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.state = state;
        this.description = description;
        this.discussions = discussions;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Integer> discussions) {
        this.discussions = discussions;
    }
}
