package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Deal;

import java.util.List;

public interface IDealService {
    List<Deal> getDealsByIds(List<Integer> ids);
    String addDeal(Deal entry);
}
