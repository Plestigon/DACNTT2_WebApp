package tdtu.ems.hr_service.models;

import java.util.Date;

public class Contract {
    private int id;
    private String code;
    private int ownerId;
    private int type;   //ContractType
    private int department;
    private Date timeStart;
    private Date timeEnd;
    private int status; //ContractStatus

    public Contract() {}

    public Contract(String code, int ownerId, int type, int department, Date timeStart, Date timeEnd, int status) {
        this.code = code;
        this.ownerId = ownerId;
        this.type = type;
        this.department = department;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
