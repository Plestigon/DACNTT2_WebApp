package tdtu_ems.department_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu_ems.main.models.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class DepartmentService {
    private final Firestore db;
    private final Logger logger;

    public DepartmentService() {
        db = FirestoreClient.getFirestore();
        logger = LoggerFactory.getLogger(DepartmentService.class);
    }

    public String addDepartment(Department department) throws ExecutionException, InterruptedException {
        DocumentReference idTracerDoc = db.collection("idTracer").document("departments");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        department.setId((int) id);
        ApiFuture<WriteResult> result = db.collection("departments")
                .document(String.valueOf(id)).set(department);
        logger.info("addDepartment update idTracer: " +
                idTracerDoc.update("id", id).get().getUpdateTime());
        return result.get().getUpdateTime().toString();
    }

    public String removeDepartment(int id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = db.collection("departments").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    public List<Department> getDepartments() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = db.collection("departments").get().get().getDocuments();
        List<Department> departments = new ArrayList<>();
        for (DocumentSnapshot data : documents) {
            departments.add(data.toObject(Department.class));
        }
        return departments;
    }

    public Department getDepartmentById(int id) throws ExecutionException, InterruptedException {
        DocumentSnapshot data = db.collection("departments").document(String.valueOf(id)).get().get();
        Department department = null;
        if (data.exists()) {
            department = data.toObject(Department.class);
        }
        return department;
    }

    public Department getDepartmentByShortName(String shortName) throws ExecutionException, InterruptedException {
        QuerySnapshot query = db.collection("departments")
                .whereEqualTo("shortName", shortName).get().get();
        List<QueryDocumentSnapshot> documents = query.getDocuments();
        return documents.isEmpty() ? null : documents.get(0).toObject(Department.class);
    }
}
