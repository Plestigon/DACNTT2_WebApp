package tdtu.ems.finance_service.models;

import tdtu.ems.finance_service.utils.Enums;

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
    private String contactName;
    private double dealValue;
    private Date createDate;
    private Date closeDate;

    public DealResult() {}

    public DealResult(Deal o) {
        this.id = o.getId();
        this.title = o.getTitle();
        this.stage = o.getStage();
        this.stageName = Enums.DealStage.values()[o.getStage()].name();
        this.associate = o.getAssociate();
        this.associateName = o.getAssociateName();
        this.contact = o.getContact();
        this.contactName = o.getContactName();
        this.dealValue = o.getDealValue();
        this.createDate = o.getCreateDate();
        this.closeDate = o.getCloseDate();
        this.associateName = associateName;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
