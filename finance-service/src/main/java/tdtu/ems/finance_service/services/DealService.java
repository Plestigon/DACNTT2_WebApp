package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.models.DealResult;
import tdtu.ems.finance_service.models.DealStageDetail;
import tdtu.ems.finance_service.repositories.DealRepository;
import tdtu.ems.finance_service.repositories.IAssociateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DealService implements IDealService {
    private final DealRepository _dealRepository;
    private final IAssociateRepository _associateRepository;
    private final Logger<DealService> _logger;

    public DealService(DealRepository dealRepository, IAssociateRepository associateRepository) {
        _dealRepository = dealRepository;
        _associateRepository = associateRepository;
        _logger = new Logger<>(DealService.class);
    }

    @Override
    public DealResult getDealById(int id) throws ExecutionException, InterruptedException {
        try {
            return _dealRepository.getDealById(id);
        }
        catch (Exception e) {
            _logger.Error("getDealsByIds", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DealResult> getDealsByAssociateId(int id) throws ExecutionException, InterruptedException {
        try {
            List<DealResult> result = _dealRepository.getDealsByAssociateId(id);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getDealsByIds", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DealStageDetail> getDealStageDetailsByDealId(int id) throws ExecutionException, InterruptedException {
        try {
            List<DealStageDetail> result = _dealRepository.getDealStageDetailsByDealId(id);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getDealStageDetailsByDealId", e.getMessage());
            throw e;
        }
    }

    @Override
    public int addDeal(Deal entry) throws ExecutionException, InterruptedException {
        try {
            int dealId = _dealRepository.addDeal(entry);
            //Update associate dealIds
            String updateResult = _associateRepository.updateAssociateDealId(entry.getAssociate(), dealId, true);
            return dealId;
        }
        catch (Exception e) {
            _logger.Error("addDeal", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeDeal(int id) throws ExecutionException, InterruptedException {
        try {
            Deal removedDeal = _dealRepository.removeDeal(id);
            //Update associate dealIds
            String updateResult = _associateRepository.updateAssociateDealId(removedDeal.getAssociate(), id, false);
            //Delete deal stage details
            String updateResult2 = _dealRepository.removeDealStageDetails(removedDeal.getDealStageDetails());
            return updateResult + " /// " + updateResult2;
        }
        catch (Exception e) {
            _logger.Error("removeDeal", e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateDealNotes(int id, String value) throws ExecutionException, InterruptedException {
        try {
            return _dealRepository.updateDealNotes(id, value);
        }
        catch (Exception e) {
            _logger.Error("updateDealNotes", e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateDealStage(int id, int value) throws ExecutionException, InterruptedException {
        try {
            return _dealRepository.updateDealStage(id, value);
        }
        catch (Exception e) {
            _logger.Error("updateDealStage", e.getMessage());
            throw e;
        }
    }
}
