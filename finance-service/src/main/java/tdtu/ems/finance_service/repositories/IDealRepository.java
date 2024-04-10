package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.models.DealResult;

import java.util.List;

public interface IDealRepository {
    List<DealResult> getDealsByIds(List<Integer> ids);
}
