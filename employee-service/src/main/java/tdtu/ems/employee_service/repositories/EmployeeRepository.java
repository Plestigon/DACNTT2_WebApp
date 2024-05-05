package tdtu.ems.employee_service.repositories;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.services.EmployeeService;
import tdtu.ems.employee_service.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private final Firestore _db;
    private final Logger<EmployeeRepository> _logger;

    public EmployeeRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(EmployeeRepository.class);
    }

    @Override
    public List<Employee> getEmployees(List<Integer> ids) {
        CollectionReference employeesDb = _db.collection("employees");
        List<Employee> employees = new ArrayList<>();
        try {
            if (ids == null || ids.isEmpty()) {
                for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
                    employees.add(data.toObject(Employee.class));
                }
            }
            else {
                for (int id : ids) {
                    employees.add(employeesDb.document(String.valueOf(id)).get().get().toObject(Employee.class));
                }
                _logger.Info("getEmployeesByIds", "TEST");
            }
            return employees;
        }
        catch (Exception e) {
            _logger.Error("getEmployees", e.getMessage());
            return null;
        }
    }

    public List<Employee> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        List<Employee> employees = new ArrayList<>();
        for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
            int id = Objects.requireNonNull(data.getLong("id")).intValue();
            if (!ids.contains(id)) {
                employees.add(data.toObject(Employee.class));
            }
        }
        return employees;
    }

//    @Override
//    public List<Employee> getEmployeesByIds(List<Integer> ids) {
//        CollectionReference employeesDb = _db.collection("employees");
//        List<Employee> employees = new ArrayList<>();
//        try {
//
//        }
//        catch (Exception e) {
//            _logger.Error("getEmployeesByIds", e.getMessage());
//            return null;
//        }
//    }

    @Override
    public ProjectUpdateEmployeeDataResult getProjectUpdateEmployeeData(int writerId, List<Integer> checkIds) {
        CollectionReference employeesDb = _db.collection("employees");
        ProjectUpdateEmployeeDataResult res = new ProjectUpdateEmployeeDataResult();
        try {
            res.writerName = employeesDb.document(String.valueOf(writerId)).get().get().getString("name");
            for(int checkedId : checkIds) {
                res.checked.add(employeesDb.document(String.valueOf(checkedId)).get().get().getString("name"));
            }
            return res;
        }
        catch (Exception e) {
            _logger.Error("getProjectUpdateEmployeeData", e.getMessage());
        }
        return null;
    }
}
