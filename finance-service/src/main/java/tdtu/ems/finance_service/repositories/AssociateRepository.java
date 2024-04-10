package tdtu.ems.finance_service.repositories;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Associate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssociateRepository implements IAssociateRepository {
    private final Firestore _db;
    private final Logger<AssociateRepository> _logger;

    public AssociateRepository(Firestore db) {
        _db = db;
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
}
