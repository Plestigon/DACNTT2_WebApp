package tdtu.ems.main.models.hr;

import java.util.Date;

public class FormSubmitDto {
    private int type;
    private int ownerId;
    private Date startDate;
    private Date endDate;
    private String reason;

    public FormSubmitDto() {}

    public FormSubmitDto(int type, int ownerId, Date startDate, Date endDate, String reason) {
        this.type = type;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
