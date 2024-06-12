package tdtu.ems.finance_service.models;

import java.util.Date;
import java.util.List;

public class DealResult{
    private int id;
    private int stage;
    private int associate;
    private int contact;
    private double dealValue;
    private Date closeDate;
    private List<DealStageDetail> dealStageDetailsObj;

    public DealResult() {}

    public DealResult(Deal o, List<DealStageDetail> dealStageDetailsObj) {
        this.id = o.getId();
        this.stage = o.getStage();
        this.associate = o.getAssociate();
        this.contact = o.getContact();
        this.dealValue = o.getDealValue();
        this.closeDate = o.getCloseDate();
        this.dealStageDetailsObj = dealStageDetailsObj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getAssociate() {
        return associate;
    }

    public void setAssociate(int associate) {
        this.associate = associate;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public double getDealValue() {
        return dealValue;
    }

    public void setDealValue(double dealValue) {
        this.dealValue = dealValue;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public List<DealStageDetail> getDealStageDetailsObj() {
        return dealStageDetailsObj;
    }

    public void setDealStageDetailsObj(List<DealStageDetail> dealStageDetailsObj) {
        this.dealStageDetailsObj = dealStageDetailsObj;
    }
}
