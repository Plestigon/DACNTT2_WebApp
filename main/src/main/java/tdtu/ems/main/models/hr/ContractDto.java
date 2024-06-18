package tdtu.ems.main.models.hr;

import java.util.Date;

public class ContractDto {
    private int ownerId;
    private int type;   //ContractType
    private int department;
    private Date timeStart;
    private Date timeEnd;

    public ContractDto() {}

    public ContractDto(int ownerId, int type, int department, Date timeStart, Date timeEnd) {
        this.ownerId = ownerId;
        this.type = type;
        this.department = department;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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
}
