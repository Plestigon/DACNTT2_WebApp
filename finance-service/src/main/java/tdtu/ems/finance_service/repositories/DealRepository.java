package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.Deal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class DealRepository implements IDealRepository {
    private final Firestore _db;
    private final Logger<DealRepository> _logger;

    public DealRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(DealRepository.class);
    }

    @Override
    public List<Deal> getDealsByIds(List<Integer> ids) {
        try {
            CollectionReference dealsDb = _db.collection("deals");
            List<Deal> deals = new ArrayList<>();
            for (int id : ids) {
                Deal deal = dealsDb.document(String.valueOf(id)).get().get().toObject(Deal.class);
                deals.add(deal);
            }
            return deals;
        }
        catch (Exception e) {
            _logger.Error("getDealsByIds", e.getMessage());
            return null;
        }
    }

    @Override
    public String addDeal(Deal entry) {
        try {
            CollectionReference dealsDb = _db.collection("deals");
            DocumentReference idTracer = _db.collection("idTracer").document("deals");
            long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            entry.setId((int) id);
            ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).set(entry);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            _logger.Error("addDeal", e.getMessage());
            return null;
        }
    }
}
