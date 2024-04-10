package tdtu.ems.finance_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;
import java.util.List;

public class Deal {
    protected int id;
    protected Enums.DealStage stage;
    protected int associate;
    protected int contact;
    protected double dealValue;
    protected Date closeDate;
    protected List<Integer> dealStageDetails;

    public Deal() {}

    public Deal(int id, Enums.DealStage stage, int associate, int contact, double dealValue, Date closeDate, List<Integer> dealStageDetails) {
        this.id = id;
        this.stage = stage;
        this.associate = associate;
        this.contact = contact;
        this.dealValue = dealValue;
        this.closeDate = closeDate;
        this.dealStageDetails = dealStageDetails;
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

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public List<Integer> getDealStageDetails() {
        return dealStageDetails;
    }

    public void setDealStageDetails(List<Integer> dealStageDetails) {
        this.dealStageDetails = dealStageDetails;
    }
}
