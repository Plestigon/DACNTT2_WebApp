package tdtu.ems.employee_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.services.EmployeeService;
import tdtu.ems.employee_service.models.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private final Firestore _db;
    private final Logger<EmployeeRepository> _logger;
    private final PasswordEncoder _passwordEncoder;

    public EmployeeRepository(PasswordEncoder passwordEncoder) {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(EmployeeRepository.class);
        _passwordEncoder = passwordEncoder;
    }

    public Employee addEmployee(Employee e) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        //region Check if email already used
        QuerySnapshot query = employeesDb.whereEqualTo("email", e.getEmail()).get().get();
        if (!query.getDocuments().isEmpty()) {
            throw new RuntimeException("Email already exists");
        }
        //endregion

        DocumentReference idTracerDoc = _db.collection("idTracer").document("employees");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        e.setId((int) id);
        e.setJoinDate(new Date());
        //Encrypt password
        String hashedPassword = _passwordEncoder.encode(e.getPassword());
        e.setPassword(hashedPassword);

        ApiFuture<WriteResult> result = employeesDb.document(String.valueOf(id)).set(e);
        ApiFuture<WriteResult> resultUpdId = idTracerDoc.update("id", id);
        return e;
    }

    @Override
    public List<EmployeeResult> getEmployees(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        List<EmployeeResult> employees = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
                Employee e = data.toObject(Employee.class);
                if (e != null) {
                    employees.add(new EmployeeResult(e));
                }
            }
        }
        else {
            for (int id : ids) {
                Employee e = employeesDb.document(String.valueOf(id)).get().get().toObject(Employee.class);
                if (e != null) {
                    employees.add(new EmployeeResult(e));
                }
            }
        }
        return employees;
    }

    public List<EmployeeResult> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        List<EmployeeResult> employees = new ArrayList<>();
        for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
            int id = Objects.requireNonNull(data.getLong("id")).intValue();
            if (!ids.contains(id)) {
                Employee e = data.toObject(Employee.class);
                if (e != null) {
                    employees.add(new EmployeeResult(e));
                }
            }
        }
        return employees;
    }

    @Override
    public Employee getEmployeeByEmail(String email) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        var data = employeesDb.whereEqualTo("email", email).get().get();
        if (data.isEmpty()) {
            throw new RuntimeException("Invalid email");
        }
        return data.getDocuments().get(0).toObject(Employee.class);
    }

    @Override
    public String removeEmployee(int id) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        if (employeesDb.document(String.valueOf(id)).get().get() == null) {
            throw new RuntimeException("Employee not found");
        }
        ApiFuture<WriteResult> result = _db.collection("employees").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String changePassword(int id, String newPassword) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        String passwordHash = _passwordEncoder.encode(newPassword);
        ApiFuture<WriteResult> result = employeesDb.document(String.valueOf(id)).update("password", passwordHash);
        return result.get().getUpdateTime().toString();
    }
}
