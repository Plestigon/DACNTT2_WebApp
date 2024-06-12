package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.Deal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class DealRepository implements IDealRepository {
    private final Firestore _db;
    private final Logger<DealRepository> _logger;

    public DealRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(DealRepository.class);
    }

    @Override
    public List<Deal> getDealsByAssociateId(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        List<Deal> deals = new ArrayList<>();
        for (QueryDocumentSnapshot data : dealsDb.get().get().getDocuments()) {
            Deal deal = data.toObject(Deal.class);
            if (deal.getAssociate() == id) {
                deals.add(deal);
            }
        }
        return deals;
    }

    @Override
    public String addDeal(Deal entry) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        DocumentReference idTracer = _db.collection("idTracer").document("deals");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String removeDeal(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        if (dealsDb.document((String.valueOf(id))).get().get() == null) {
            throw new RuntimeException("Deal not found");
        }
        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }
}
