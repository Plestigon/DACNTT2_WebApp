package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.repositories.DealRepository;

import java.util.List;

@Service
public class DealService implements IDealService {
    private final DealRepository _dealRepository;

    public DealService(DealRepository dealRepository) {
        _dealRepository = dealRepository;
    }

    @Override
    public List<Deal> getDealsByIds(List<Integer> ids) {
        List<Deal> result = _dealRepository.getDealsByIds(ids);
        return result;
    }

    @Override
    public String addDeal(Deal entry) {
        return _dealRepository.addDeal(entry);
    }
}
