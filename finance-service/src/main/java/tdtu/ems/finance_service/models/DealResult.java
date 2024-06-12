package tdtu.ems.finance_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;
import java.util.List;

public class DealResult{
    private int id;
    private String title;
    private int stage;
    private String stageName;
    private int associate;
    private String associateName;
    private int contact;
    private double dealValue;
    private Date closeDate;
    private List<DealStageDetail> dealStageDetailsObj;

    public DealResult() {}

    public DealResult(Deal o, String associateName, List<DealStageDetail> dealStageDetailsObj) {
        this.id = o.getId();
        this.title = o.getTitle();
        this.stage = o.getStage();
        this.stageName = Enums.DealStage.values()[o.getStage()].name();
        this.associate = o.getAssociate();
        this.associateName = associateName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getAssociate() {
        return associate;
    }

    public void setAssociate(int associate) {
        this.associate = associate;
    }

    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
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
