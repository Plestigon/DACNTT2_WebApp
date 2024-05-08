package tdtu.ems.hr_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;

public class Contract {
    private int id;
    private int code;
    private int type;
    private String typeName;
    private int department;
    private int departmentName;
    private Date timeStart;
    private Date timeEnd;
    private int status;

    public Contract() { }

    public Contract(int code, int type, String typeName, int department, int departmentName, Date timeStart, Date timeEnd, int status) {
        this.code = code;
        this.type = type;
        this.typeName = typeName;
        this.department = department;
        this.departmentName = departmentName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(int departmentName) {
        this.departmentName = departmentName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
