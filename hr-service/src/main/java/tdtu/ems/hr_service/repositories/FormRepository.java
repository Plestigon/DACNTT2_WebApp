package tdtu.ems.hr_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.hr_service.utils.Enums;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class FormRepository implements IFormRepository {
    private final Firestore _db;
    private final Logger<FormRepository> _logger;

    public FormRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(FormRepository.class);
    }

    @Override
    public Integer addForm(Form entry) throws ExecutionException, InterruptedException {
        CollectionReference formsDb = _db.collection("forms");
        DocumentReference idTracer = _db.collection("idTracer").document("forms");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        entry.setCreateDate(new Date());
        entry.setStatus(Enums.FormStatus.WaitingForApproval.ordinal());
        ApiFuture<WriteResult> result = formsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return (int) id;
    }

    @Override
    public List<Form> getFormsByEmployeeId(int id) throws ExecutionException, InterruptedException {
        CollectionReference formsDb = _db.collection("forms");
        List<Form> forms = new ArrayList<>();
        for (DocumentSnapshot data : formsDb.get().get().getDocuments()) {
            Form form = data.toObject(Form.class);
            if (form != null && form.getOwnerId() == id) {
                forms.add(form);
            }
        }
        return forms;
    }
}
