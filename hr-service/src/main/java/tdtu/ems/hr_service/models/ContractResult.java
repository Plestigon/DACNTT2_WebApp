package tdtu.ems.hr_service.models;

import tdtu.ems.hr_service.utils.Enums;

import java.util.Date;

public class ContractResult {
    private int id;
    private String code;
    private int ownerId;
    private String ownerName;
    private int type;   //ContractType
    private String typeName;
    private int department;
    private String departmentName;
    private Date timeStart;
    private Date timeEnd;
    private int status; //ContractStatus
    private String statusName;

    public ContractResult(Contract c, String ownerName, String departmentName) {
        this.id = c.getId();
        this.code = c.getCode();
        this.type = c.getType();
        this.typeName = Enums.ContractType.values()[c.getType()].name;
        this.department = c.getDepartment();
        this.ownerId = c.getOwnerId();
        this.ownerName = ownerName;
        this.departmentName = departmentName;
        this.timeStart = c.getTimeStart();
        this.timeEnd = c.getTimeEnd();
        this.status = c.getStatus();
        this.statusName = Enums.ContractStatus.values()[c.getStatus()].name();
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
