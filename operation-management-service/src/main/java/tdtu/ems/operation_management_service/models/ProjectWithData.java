package tdtu.ems.operation_management_service.models;

import com.google.firebase.database.annotations.NotNull;

import java.util.Date;

public class ProjectWithData extends Project {
    private String ownerName;

    public ProjectWithData() {
        super();
    }

    public ProjectWithData(@NotNull Project p) {
        super(p);
    }

    public ProjectWithData(String name, int ownerId, int status, Date dueDate, String description, String ownerName) {
        super(name, ownerId, status, dueDate, description);
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
