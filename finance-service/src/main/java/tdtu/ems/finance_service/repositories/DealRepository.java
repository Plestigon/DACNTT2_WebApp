package tdtu.ems.finance_service.repositories;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.models.DealResult;

import java.util.List;

@Repository
public class DealRepository implements IDealRepository {
    private final Firestore _db;
    private final Logger<DealRepository> _logger;

    public DealRepository(Firestore db) {
        _db = db;
        _logger = new Logger<>(DealRepository.class);
    }

    @Override
    public List<DealResult> getDealsByIds(List<Integer> ids) {
        return null;
    }
}
