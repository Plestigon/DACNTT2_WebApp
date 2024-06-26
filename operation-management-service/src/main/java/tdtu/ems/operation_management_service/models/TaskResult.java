package tdtu.ems.operation_management_service.models;

import tdtu.ems.operation_management_service.utils.Enums;

import java.util.Date;
import java.util.List;

public class TaskResult {
    private int id;
    private String name;
    private int projectId;
    private String projectName;
    private int assigneeId;
    private String assigneeName;
    private Date createDate;
    private Date updateDate;
    private Date dueDate;
    private int priority;
    private String priorityName;
    private int state;  //TaskState
    private String stateName;
    private String description;
    private List<Integer> discussions;

    public TaskResult(Task t, String assigneeName) {
        this.id = t.getId();
        this.name = t.getName();
        this.projectId = t.getProjectId();
        this.assigneeId = t.getAssigneeId();
        this.assigneeName = assigneeName;
        this.createDate = t.getCreateDate();
        this.updateDate = t.getUpdateDate();
        this.dueDate = t.getDueDate();
        this.priority = t.getPriority();
        this.priorityName = Enums.Priority.values()[t.getPriority()].name();
        this.state = t.getState();
        this.stateName = Enums.TaskState.values()[t.getState()].name;
        this.description = t.getDescription();
        this.discussions = t.getDiscussions();
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }
}
