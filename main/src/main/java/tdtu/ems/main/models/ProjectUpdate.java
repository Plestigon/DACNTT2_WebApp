package tdtu.ems.main.models;

import java.util.Date;
import java.util.List;

public class ProjectUpdate {
    private int id;
    private int writerId;
    private String comment;
    private Date createTime;
    private List<Integer> checkedIds;

    public ProjectUpdate() {}

    public ProjectUpdate(int writerId, String comment, Date createTime) {
        this.writerId = writerId;
        this.comment = comment;
        this.createTime = createTime;
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

    public List<Integer> getCheckedIds() {
        return checkedIds;
    }

    public void setCheckedIds(List<Integer> checkedIds) {
        this.checkedIds = checkedIds;
    }
}
