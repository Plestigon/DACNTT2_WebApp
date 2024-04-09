package tdtu.ems.finance_service.configs.models;

import tdtu.ems.core_service.models.Enums;

import java.util.Date;

public class DealStageDetail {
    private int id;
    private int dealId;
    private Enums.DealStage stage;
    private Date startDate;
    private Date endDate;
    private String notes;

    public DealStageDetail() {}

    public DealStageDetail(int id, int dealId, Enums.DealStage stage, Date startDate, Date endDate, String notes) {
        this.id = id;
        this.dealId = dealId;
        this.stage = stage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public Enums.DealStage getStage() {
        return stage;
    }

    public void setStage(Enums.DealStage stage) {
        this.stage = stage;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
