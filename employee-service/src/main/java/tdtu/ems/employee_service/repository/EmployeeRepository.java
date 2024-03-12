package tdtu.ems.employee_service.repository;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdtu.ems.employee_service.services.EmployeeService;
import tdtu.ems.main.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EmployeeRepository implements IEmployeeRepository {
    private final Firestore _db;
    private final Logger _logger;

    public EmployeeRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = LoggerFactory.getLogger(EmployeeService.class);
    }

    @Override
    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        List<Employee> employees = new ArrayList<>();
        for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
            employees.add(data.toObject(Employee.class));
        }
        return employees;
    }
}
