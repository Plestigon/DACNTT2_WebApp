package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Deal;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDealService {
    List<Deal> getDealsByIds(List<Integer> ids) throws ExecutionException, InterruptedException;
    String addDeal(Deal entry) throws ExecutionException, InterruptedException;
    String removeDeal(int id) throws ExecutionException, InterruptedException;
}
