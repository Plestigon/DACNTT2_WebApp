package tdtu_ems.userservice.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu_ems.userservice.models.Department;

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
        logger = LoggerFactory.getLogger(UserService.class);
    }

    public String addDepartment(Department department) throws ExecutionException, InterruptedException {
        DocumentReference idTracerDoc = db.collection("id_tracer").document("departments");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        department.setId((int) id);
        ApiFuture<WriteResult> result = db.collection("departments")
                .document(String.valueOf(id)).set(department);
        logger.info("addDepartment update id_tracer: " +
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

    public String addUserToDepartment(int userId, int departmentId) throws ExecutionException, InterruptedException {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            List<Integer> userIds = new ArrayList<>();
            if (department.getUserIds() != null) {
                userIds = department.getUserIds();
                if (userIds.contains(userId)) {
                    String msg = "This userId already exist in this Department.";
                    logger.error("addUserToDepartment: " + msg);
                    return msg;
                }
            }
            userIds.add(userId);
            ApiFuture<WriteResult> result = db.collection("departments")
                    .document(String.valueOf(departmentId)).update("userIds", userIds);
            return result.get().toString();
        }
        String msg = "Department Not Found.";
        logger.error("addUserToDepartment: " + msg);
        return msg;
    }

    public String removeUserFromDepartment(int userId, int departmentId) throws ExecutionException, InterruptedException {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            List<Integer> userIds = new ArrayList<>();
            if (department.getUserIds() != null) {
                userIds = department.getUserIds();
                if (!userIds.contains(userId)) {
                    String msg = "This userId does not exist in this Department.";
                    logger.error("removeUserFromDepartment: " + msg);
                    return msg;
                }
                userIds.remove(Integer.valueOf(userId));
            }
            ApiFuture<WriteResult> result = db.collection("departments")
                    .document(String.valueOf(departmentId)).update("userIds", userIds);
            return result.get().toString();
        }
        String msg = "Department Not Found.";
        logger.error("addUserToDepartment: " + msg);
        return msg;
    }
}
