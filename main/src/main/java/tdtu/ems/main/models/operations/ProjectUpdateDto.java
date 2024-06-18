package tdtu.ems.main.models.operations;

public class ProjectUpdateDto {
    private int writerId;
    private String comment;

    public ProjectUpdateDto() {}

    public ProjectUpdateDto(int writerId, String comment) {
        this.writerId = writerId;
        this.comment = comment;
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
}
