package tdtu_ems.employee_service.models;

import java.util.List;

public class Department {
    private int id;
    private String shortName;
    private String longName;
    private List<Integer> employeeIds;

    public Department(){}

    public Department(int id, String shortName, String longName, List<Integer> employeeIds) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.employeeIds = employeeIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }
}