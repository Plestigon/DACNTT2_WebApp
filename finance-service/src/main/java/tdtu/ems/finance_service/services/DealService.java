package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.repositories.DealRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DealService implements IDealService {
    private final DealRepository _dealRepository;
    private final Logger<DealService> _logger;

    public DealService(DealRepository dealRepository) {
        _dealRepository = dealRepository;
        _logger = new Logger<>(DealService.class);
    }

    @Override
    public List<Deal> getDealsByIds(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<Deal> result = _dealRepository.getDealsByIds(ids);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getDealsByIds", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addDeal(Deal entry) throws ExecutionException, InterruptedException {
        try {
            return _dealRepository.addDeal(entry);
        }
        catch (Exception e) {
            _logger.Error("addDeal", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeDeal(int id) throws ExecutionException, InterruptedException {
        try {
            return _dealRepository.removeDeal(id);
        }
        catch (Exception e) {
            _logger.Error("removeDeal", e.getMessage());
            throw e;
        }
    }
}
