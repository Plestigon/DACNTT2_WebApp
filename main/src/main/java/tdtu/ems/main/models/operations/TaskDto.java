package tdtu.ems.main.models.operations;

import java.util.Date;

public class TaskDto {
    private String name;
    private int projectId;
    private int assigneeId;
    private int priority;
    private String description;

    public TaskDto() {}

    public TaskDto(String name, int projectId, int assigneeId, int priority, String description) {
        this.name = name;
        this.projectId = projectId;
        this.assigneeId = assigneeId;
        this.priority = priority;
        this.description = description;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
