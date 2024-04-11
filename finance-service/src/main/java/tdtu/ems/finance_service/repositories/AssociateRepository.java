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

@Repository
public class AssociateRepository implements IAssociateRepository {
    private final Firestore _db;
    private final Logger<AssociateRepository> _logger;

    public AssociateRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(AssociateRepository.class);
    }

    @Override
    public List<Associate> getAssociates() {
        try {
            CollectionReference associatesDb = _db.collection("associates");
            List<Associate> associates = new ArrayList<>();
            for (DocumentSnapshot data : associatesDb.get().get().getDocuments()) {
                associates.add(data.toObject(Associate.class));
            }
            return associates;
        }
        catch (Exception e) {
            _logger.Error("getAssociates", e.getMessage());
            return null;
        }
    }

    @Override
    public String addAssociate(Associate entry) {
        try {
            CollectionReference associatesDb = _db.collection("associates");
            DocumentReference idTracer = _db.collection("idTracer").document("associates");
            long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            entry.setId((int) id);
            ApiFuture<WriteResult> result = associatesDb.document(String.valueOf(id)).set(entry);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            _logger.Error("addAssociate", e.getMessage());
            return null;
        }
    }
}
