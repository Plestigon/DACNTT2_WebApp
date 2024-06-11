package tdtu.ems.employee_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.employee_service.models.Department;
import tdtu.ems.employee_service.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class DepartmentRepository implements IDepartmentRepository {
    private final Firestore _db;

    public DepartmentRepository() {
        _db = FirestoreClient.getFirestore();
    }

    @Override
    public String addDepartment(Department department) throws ExecutionException, InterruptedException {
        DocumentReference idTracerDoc = _db.collection("idTracer").document("departments");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        department.setId((int) id);
        ApiFuture<WriteResult> result = _db.collection("departments")
                .document(String.valueOf(id)).set(department);
        ApiFuture<WriteResult> idUpdResult = idTracerDoc.update("id", id);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String removeDepartment(int id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = _db.collection("departments").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    @Override
    public List<Department> getDepartments() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = _db.collection("departments").get().get().getDocuments();
        List<Department> departments = new ArrayList<>();
        for (DocumentSnapshot data : documents) {
            departments.add(data.toObject(Department.class));
        }
        return departments;
    }

    @Override
    public Department getDepartmentById(int id) throws ExecutionException, InterruptedException {
        DocumentSnapshot data = _db.collection("departments").document(String.valueOf(id)).get().get();
        return data.toObject(Department.class);
    }

    @Override
    public Department getDepartmentByShortName(String shortName) throws ExecutionException, InterruptedException {
        QuerySnapshot query = _db.collection("departments")
                .whereEqualTo("shortName", shortName).get().get();
        List<QueryDocumentSnapshot> documents = query.getDocuments();
        return documents.get(0).toObject(Department.class);
    }

    @Override
    public String addEmployeeToDepartment(int employeeId, int departmentId) throws ExecutionException, InterruptedException {
        CollectionReference departmentsDb = _db.collection("departments");
        Department d = departmentsDb.document(String.valueOf(departmentId)).get().get().toObject(Department.class);
        if (d != null) {
            List<Integer> empIds = d.getEmployeeIds();
            if (empIds == null) empIds = new ArrayList<>();
            if (!empIds.contains(employeeId)) {
                empIds.add(employeeId);
            }
            ApiFuture<WriteResult> result = departmentsDb.document(String.valueOf(departmentId)).set(d);
            return result.get().getUpdateTime().toString();
        }
        throw new RuntimeException("Department with id " + departmentId + " not found");
    }

    @Override
    public String removeEmployeeFromDepartments(int employeeId) throws ExecutionException, InterruptedException {
        CollectionReference departmentsDb = _db.collection("departments");
        String results = "";
        for (QueryDocumentSnapshot data : departmentsDb.get().get().getDocuments()) {
            Department d = data.toObject(Department.class);
            List<Integer> empIds = d.getEmployeeIds();
            if (empIds.contains(employeeId)) {
                empIds.remove(Integer.valueOf(employeeId));
                ApiFuture<WriteResult> result = departmentsDb.document(String.valueOf(d.getId())).update("employeeIds", empIds);
                results += result.get().getUpdateTime().toString() + ";";
            }
        }
        return results;
    }
}
