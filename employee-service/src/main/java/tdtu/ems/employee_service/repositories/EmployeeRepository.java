package tdtu.ems.employee_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.employee_service.models.EmployeeResult;
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

    public int addEmployee(Employee e) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        //region Check if email already used
        QuerySnapshot query = employeesDb.whereEqualTo("email", e.getEmail()).get().get();
        if (!query.getDocuments().isEmpty()) {
            return -1;
        }
        //endregion
        DocumentReference idTracerDoc = _db.collection("idTracer").document("employees");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        e.setId((int) id);
        ApiFuture<WriteResult> result = employeesDb.document(String.valueOf(id)).set(e);
        ApiFuture<WriteResult> resultUpdId = idTracerDoc.update("id", id);
        _logger.Info("addEmployee", "update idTracer: " + resultUpdId.get().getUpdateTime());
        //Add employee to department's employee list
        //_logger.info("addEmployeeToDepartment: " + addEmployeeToDepartment((int) id, employee.getDepartmentId()));
        return (int) id;
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
        Employee result = employeesDb.whereEqualTo("email", email).get().get().toObjects(Employee.class).getFirst();
        return result;
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
