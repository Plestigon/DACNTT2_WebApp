package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Associate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class AssociateRepository implements IAssociateRepository {
    private final Firestore _db;
    private final Logger<AssociateRepository> _logger;

    public AssociateRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(AssociateRepository.class);
    }

    @Override
    public List<Associate> getAssociates() throws ExecutionException, InterruptedException {
        CollectionReference associatesDb = _db.collection("associates");
        List<Associate> associates = new ArrayList<>();
        for (DocumentSnapshot data : associatesDb.get().get().getDocuments()) {
            associates.add(data.toObject(Associate.class));
        }
        return associates;
    }

    @Override
    public Associate getAssociateById(int id) throws ExecutionException, InterruptedException {
        CollectionReference associatesDb = _db.collection("associates");
        return associatesDb.document(String.valueOf(id)).get().get().toObject(Associate.class);
    }

    @Override
    public String addAssociate(Associate entry) throws ExecutionException, InterruptedException {
        CollectionReference associatesDb = _db.collection("associates");
        DocumentReference idTracer = _db.collection("idTracer").document("associates");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        ApiFuture<WriteResult> result = associatesDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String removeAssociate(int id) throws ExecutionException, InterruptedException {
        CollectionReference associatesDb = _db.collection("associates");
        if (associatesDb.document((String.valueOf(id))).get().get() == null) {
            throw new RuntimeException("Associate not found");
        }
        ApiFuture<WriteResult> result = associatesDb.document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String updateAssociateDealId(int associateId, int dealId, boolean isAdding) throws ExecutionException, InterruptedException {
        CollectionReference associatesDb = _db.collection("associates");
        Associate a = associatesDb.document(String.valueOf(associateId)).get().get().toObject(Associate.class);
        if (a == null) {
            throw new RuntimeException("Associate with id " + associateId + " not found");
        }
        List<Integer> dealIds = a.getDeals();
        if (isAdding && !dealIds.contains(dealId)) {
            dealIds.add(dealId);
        }
        else {
            dealIds.remove(Integer.valueOf(dealId));
        }
        ApiFuture<WriteResult> result = associatesDb.document(String.valueOf(associateId)).update("deals", dealIds);
        return result.get().getUpdateTime().toString();
    }


}
