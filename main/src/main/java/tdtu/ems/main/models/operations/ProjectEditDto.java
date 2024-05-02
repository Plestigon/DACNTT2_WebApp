package tdtu.ems.main.models.operations;

import java.util.Date;

public class ProjectEditDto {
    private int id;
    private String name;
    private Date dueDate;
    private String description;

    public ProjectEditDto(int id, String name, Date dueDate, String description) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
