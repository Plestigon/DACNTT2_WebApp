package tdtu.ems.operation_management_service.models;

import java.util.Date;
import java.util.List;

public class ProjectUpdate {
    private int id;
    private int writerId;
    private String comment;
    private Date createTime;

    public ProjectUpdate() {}

    public ProjectUpdate(int writerId, String comment) {
        this.writerId = writerId;
        this.comment = comment;
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
