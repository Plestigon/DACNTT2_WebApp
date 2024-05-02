package tdtu.ems.main.models.operations;

import java.util.Date;
import java.util.List;

public class ProjectCreateDto {
    private String name;
    private int ownerId;
    private List<Integer> memberIds;
    private Date dueDate;
    private String description;

    public ProjectCreateDto(String name, int ownerId, List<Integer> memberIds, Date dueDate, String description) {
        this.name = name;
        this.ownerId = ownerId;
        this.memberIds = memberIds;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
