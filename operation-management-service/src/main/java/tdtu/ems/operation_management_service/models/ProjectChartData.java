package tdtu.ems.operation_management_service.models;

public class ProjectChartData {
    public int id;
    public String label;
    public int value;
    public String color;

    public ProjectChartData(int id, String label, int value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public ProjectChartData(int id, String label, int value, String color) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.color = color;
    }
}
