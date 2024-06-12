package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Deal;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDealRepository {
    List<Deal> getDealsByAssociateId(int id) throws ExecutionException, InterruptedException;
    String addDeal(Deal entry) throws ExecutionException, InterruptedException;
    String removeDeal(int id) throws ExecutionException, InterruptedException;
}
