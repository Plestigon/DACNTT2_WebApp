package tdtu.ems.finance_service.configs.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;

public class Deal {
    private int id;
    private Enums.DealStage stage;
    private int associate;
    private int contact;
    private double dealValue;
    private Date startDate;
    private Date closeDate;

    public Deal() {}

    public Deal(int id, Enums.DealStage stage, int associate, int contact, double dealValue, Date startDate, Date closeDate) {
        this.id = id;
        this.stage = stage;
        this.associate = associate;
        this.contact = contact;
        this.dealValue = dealValue;
        this.startDate = startDate;
        this.closeDate = closeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Enums.DealStage getStage() {
        return stage;
    }

    public void setStage(Enums.DealStage stage) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
