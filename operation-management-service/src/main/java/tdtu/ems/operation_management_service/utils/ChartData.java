package tdtu.ems.operation_management_service.utils;

public class ChartData {
    public int id;
    public String label;
    public float value;
    public String color;

    public ChartData(int id, String label, int value, String color) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.color = color;
    }
}
