package tdtu.ems.operation_management_service.models;

import java.util.Date;

public class ProjectUpdateResult {
    private int id;
    private int writerId;
    private String writerName;
    private String comment;
    private Date createTime;

    public ProjectUpdateResult(int id, int writerId, String writerName, String comment, Date createTime) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
        this.comment = comment;
        this.createTime = createTime;
    }

    public ProjectUpdateResult(ProjectUpdate pd, String writerName) {
        this.id = pd.getId();
        this.writerId = pd.getWriterId();
        this.writerName = writerName;
        this.comment = pd.getComment();
        this.createTime = pd.getCreateTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
