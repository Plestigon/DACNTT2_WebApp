package tdtu.ems.main.models.operations;

import java.util.Date;
import java.util.List;

public class ProjectUpdateResult {
    public int id;
    public int writerId;
    public String writerName;
    public String comment;
    public Date createTime;

    public ProjectUpdateResult() {}

    public ProjectUpdateResult(int id, int writerId, String writerName, String comment, Date createTime) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
        this.comment = comment;
        this.createTime = createTime;
    }
}
