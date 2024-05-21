package tdtu.ems.hr_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;

public class FormResult {
    private int id;
    private int ownerId;
    private int type;   //FormType
    private String typeName;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String reason;
    private int status; //FormStatus
    private String statusName;
    private String notes;

    public FormResult() {}

    public FormResult(Form f) {
        this.id = f.getId();
        this.ownerId = f.getOwnerId();
        this.type = f.getType();
        this.typeName = Enums.FormType.values()[f.getType()].name;
        this.createDate = f.getCreateDate();
        this.startDate = f.getStartDate();
        this.endDate = f.getEndDate();
        this.reason = f.getReason();
        this.status = f.getStatus();
        this.statusName = Enums.FormStatus.values()[f.getStatus()].name;
        this.notes = f.getNotes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}