package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Deal;

import java.util.List;

public interface IDealRepository {
    List<Deal> getDealsByIds(List<Integer> ids);
    String addDeal(Deal entry);
}
