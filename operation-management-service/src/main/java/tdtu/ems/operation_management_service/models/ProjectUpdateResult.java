package tdtu.ems.operation_management_service.models;

import java.util.Date;

public class ProjectUpdateResult {
    public int id;
    public int writerId;
    public String writerName;
    public String comment;
    public Date createTime;

    public ProjectUpdateResult(int id, int writerId, String writerName, String comment, Date createTime) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
        this.comment = comment;
        this.createTime = createTime;
    }

    public ProjectUpdateResult(ProjectUpdate pd, String writerName) {
        this.writerId = pd.getWriterId();
        this.writerName = writerName;
        this.comment = pd.getComment();
        this.createTime = pd.getCreateTime();
    }
}
