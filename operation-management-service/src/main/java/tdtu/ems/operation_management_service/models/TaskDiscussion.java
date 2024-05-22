package tdtu.ems.operation_management_service.models;

import java.util.Date;

public class TaskDiscussion {
    private int id;
    private int taskId;
    private int writerId;   //EmployeeId
    private String writerName;
    private String writerEmail;
    private String writerRole;
    private Date createDate;
    private String content;

    public TaskDiscussion() {}

    public TaskDiscussion(int id, int taskId, int writerId, String writerName, String writerEmail, String writerRole, Date createDate, String content) {
        this.id = id;
        this.taskId = taskId;
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerEmail = writerEmail;
        this.writerRole = writerRole;
        this.createDate = createDate;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getWriterRole() {
        return writerRole;
    }

    public void setWriterRole(String writerRole) {
        this.writerRole = writerRole;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
