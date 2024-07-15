package tdtu.ems.operation_management_service.models;

public class ProjectStatusResult {
    public int status;
    public String statusName;
    public int count;

    public ProjectStatusResult(int status, String statusName, int count) {
        this.status = status;
        this.statusName = statusName;
        this.count = count;
    }
}
