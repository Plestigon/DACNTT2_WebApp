package tdtu.ems.main.models.operations;

import java.util.Date;

public class TaskDiscussionDto {
    private int taskId;
    private int writerId;   //EmployeeId
    private String content;

    public TaskDiscussionDto() {}

    public TaskDiscussionDto(int taskId, int writerId, String content) {
        this.taskId = taskId;
        this.writerId = writerId;
        this.content = content;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
