package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.models.DealResult;
import tdtu.ems.finance_service.models.DealStageDetail;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDealRepository {
    DealResult getDealById(int id) throws ExecutionException, InterruptedException;
    List<DealResult> getDealsByAssociateId(int id) throws ExecutionException, InterruptedException;
    List<DealStageDetail> getDealStageDetailsByDealId(int id) throws ExecutionException, InterruptedException;
    int addDeal(Deal entry) throws ExecutionException, InterruptedException;
    Deal removeDeal(int id) throws ExecutionException, InterruptedException;
    List<Integer> generateDealStageDetails(int dealId) throws ExecutionException, InterruptedException;
    String removeDealStageDetails(List<Integer> ids) throws ExecutionException, InterruptedException;
}
