package tdtu.ems.hr_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Repository;
import tdtu.ems.hr_service.models.FormResult;
import tdtu.ems.hr_service.utils.Enums;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.*;
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
    public PagedResponse getFormsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException {
        CollectionReference formsDb = _db.collection("forms");
        List<FormResult> forms = new ArrayList<>();
        for (DocumentSnapshot data : formsDb.get().get().getDocuments()) {
            Form form = data.toObject(Form.class);
            if (form != null && form.getOwnerId() == id) {
                forms.add(new FormResult(form));
            }
        }
        int totalCount = forms.size();
        forms.sort(Comparator.comparing(FormResult::getCreateDate).reversed());
        //Paging
        int startIndex = (page-1)*10;
        if (startIndex >= forms.size()) {
            return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
        }
        var result = forms.subList(startIndex, Math.min(startIndex + 10, forms.size()));
        return new PagedResponse(result, 200, "OK", totalCount, page, 10);
    }

    @Override
    public PagedResponse getFormsForApproval(int id, int page) throws ExecutionException, InterruptedException {
        CollectionReference formsDb = _db.collection("forms");
        CollectionReference employeesDb = _db.collection("employees");
        List<FormResult> forms = new ArrayList<>();
        for (DocumentSnapshot data : formsDb.get().get().getDocuments()) {
            Form form = data.toObject(Form.class);
            if (form != null && form.getApproverId() == id && form.getStatus() == Enums.FormStatus.WaitingForApproval.ordinal()) {
                String name = employeesDb.document(String.valueOf(form.getOwnerId())).get().get().getString("name");
                String email = employeesDb.document(String.valueOf(form.getOwnerId())).get().get().getString("email");
                forms.add(new FormResult(form, name, email));
            }
        }
        int totalCount = forms.size();
        //Paging
        int startIndex = (page-1)*10;
        if (startIndex >= forms.size()) {
            return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
        }
        var result = forms.subList(startIndex, Math.min(startIndex + 10, forms.size()));
        return new PagedResponse(result, 200, "OK", totalCount, page, 10);
    }

    @Override
    public String approveForm(int id, boolean approve) throws ExecutionException, InterruptedException {
        CollectionReference formsDb = _db.collection("forms");
        Form form = formsDb.document(String.valueOf(id)).get().get().toObject(Form.class);
        if (form != null) {
            int status;
            if (approve) {
                status = Enums.FormStatus.Approved.ordinal();
            }
            else {
                status = Enums.FormStatus.Rejected.ordinal();
            }
            ApiFuture<WriteResult> result = formsDb.document(String.valueOf(id)).update("status", status);
            return approve ? "Form Approved" : "Form Rejected";
        }
        throw new NotFoundException("Form with id " + id + "not found");
    }
}
