package tdtu.ems.finance_service.models;

import java.util.Date;
import java.util.List;

public class Deal {
    private int id;
    private String title;
    private int stage;
    private int associate;
    private int contact;
    private double dealValue;
    private Date closeDate;
    private List<Integer> dealStageDetails;

    public Deal() {}

    public Deal(int id, String title, int stage, int associate, int contact, double dealValue, Date closeDate, List<Integer> dealStageDetails) {
        this.id = id;
        this.title = title;
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
