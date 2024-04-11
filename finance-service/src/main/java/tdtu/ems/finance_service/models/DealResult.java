package tdtu.ems.finance_service.models;

import java.util.List;

public class DealResult extends Deal {
    protected List<DealStageDetail> dealStageDetailsObj;

    public DealResult(Deal d, List<DealStageDetail> dealStageDetailsObj) {
        super(d.id, d.stage, d.associate, d.contact, d.dealValue, d.closeDate, d.dealStageDetails);
        this.dealStageDetailsObj = dealStageDetailsObj;
    }

    public List<DealStageDetail> getDealStageDetailsObj() {
        return dealStageDetailsObj;
    }

    public void setDealStageDetailsObj(List<DealStageDetail> dealStageDetailsObj) {
        this.dealStageDetailsObj = dealStageDetailsObj;
    }
}
