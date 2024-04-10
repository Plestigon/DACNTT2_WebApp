package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.models.DealResult;

import java.util.List;

public interface IDealService {
    List<DealResult> getDealsByIds(List<Integer> ids);
}
