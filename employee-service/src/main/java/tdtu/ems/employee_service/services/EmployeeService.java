package tdtu.ems.employee_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tdtu.ems.employee_service.repository.EmployeeRepository;
import tdtu.ems.main.models.Department;
import tdtu.ems.main.models.Employee;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository _employeeRepository;
    private final Firestore _db;
    private final Logger _logger;

    public EmployeeService(EmployeeRepository employeeRepository) {
        _employeeRepository = employeeRepository;
        _db = FirestoreClient.getFirestore();
        _logger = LoggerFactory.getLogger(EmployeeService.class);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        try {
            CollectionReference employeesDb = _db.collection("employees");
            //region Check if email already used
            QuerySnapshot query = employeesDb.whereEqualTo("email", employee.getEmail()).get().get();
            if (!query.getDocuments().isEmpty()) {
                return null;
            }
            //endregion
            DocumentReference idTracerDoc = _db.collection("idTracer").document("employees");
            long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
            employee.setId((int) id);
            ApiFuture<WriteResult> result = employeesDb.document(String.valueOf(id)).set(employee);
            ApiFuture<WriteResult> resultUpdId = idTracerDoc.update("id", id);
            _logger.info("addEmployee update idTracer: " + resultUpdId.get().getUpdateTime());
            //Add employee to department's employee list
            _logger.info("addEmployeeToDepartment: " + addEmployeeToDepartment((int) id, employee.getDepartmentId()));
            return getEmployeeById((int) id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String removeEmployee(int id) {
        try {
            Employee employee = getEmployeeById(id);
            if (employee == null) {
                String msg = "Employee not found";
                _logger.error("removeEmployee: " + msg);
                return msg;
            }
            ApiFuture<WriteResult> result = _db.collection("employees").document(String.valueOf(id)).delete();
            //Remove employee from department's employee list
            _logger.info("removeEmployeeFromDepartment: " + removeEmployeeFromDepartment(id, employee.getDepartmentId()));
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            return _employeeRepository.getEmployees();
        }
        catch (Exception e) {
            _logger.error(e.toString());
            return null;
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        try {
            DocumentSnapshot data = _db.collection("employees").document(String.valueOf(id)).get().get();
            Employee employee = null;
            if (data.exists()) {
                employee = data.toObject(Employee.class);
            }
            return employee;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Employee> getEmployeesByIds(List<Integer> employeeIds) {
        try {
            if (employeeIds.isEmpty()) {
                return null;
            }
            QuerySnapshot query = _db.collection("employees")
                    .whereIn("id", employeeIds).get().get();
            List<QueryDocumentSnapshot> documents = query.getDocuments();
            if (documents.isEmpty()) {
                return null;
            }
            List<Employee> employees = new ArrayList<>();
            for (DocumentSnapshot data : documents) {
                employees.add(data.toObject(Employee.class));
            }
            return employees;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        try {
            QuerySnapshot query = _db.collection("employees")
                    .whereEqualTo("email", email).get().get();
            List<QueryDocumentSnapshot> documents = query.getDocuments();
            return documents.isEmpty() ? null : documents.get(0).toObject(Employee.class);
        }
        catch (Exception e) {
            return null;
        }
    }

//    public List<Employee> getEmployeesByDepartment(String shortName) throws ExecutionException, InterruptedException {
//        Department department = departmentService.getDepartmentByShortName(shortName);
//        if (department == null || department.getEmployeeIds() == null) {
//            logger.error("getemployeesByDepartment: " + "Department not found");
//            return null;
//        }
//        return getEmployeesByIds(department.getEmployeeIds());
//    }

    @Override
    public Department getDepartmentById(int id) {
        try {
            DocumentSnapshot data = _db.collection("departments").document(String.valueOf(id)).get().get();
            Department department = null;
            if (data.exists()) {
                department = data.toObject(Department.class);
            }
            return department;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String addEmployeeToDepartment(int employeeId, int departmentId) {
        try {
            Department department = getDepartmentById(departmentId);
            if (department != null) {
                List<Integer> employeeIds = new ArrayList<>();
                if (department.getEmployeeIds() != null) {
                    employeeIds = department.getEmployeeIds();
                    if (employeeIds.contains(employeeId)) {
                        String msg = "This employeeId already exist in this Department.";
                        _logger.error("addEmployeeToDepartment: " + msg);
                        return msg;
                    }
                }
                employeeIds.add(employeeId);
                ApiFuture<WriteResult> result = _db.collection("departments")
                        .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
                return result.get().toString();
            }
            String msg = "Department Not Found.";
            _logger.error("addEmployeeToDepartment: " + msg);
            return msg;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String removeEmployeeFromDepartment(int employeeId, int departmentId) {
        try {
            Department department = getDepartmentById(departmentId);
            if (department != null) {
                List<Integer> employeeIds = new ArrayList<>();
                if (department.getEmployeeIds() != null) {
                    employeeIds = department.getEmployeeIds();
                    if (!employeeIds.contains(employeeId)) {
                        String msg = "This employeeId does not exist in this Department.";
                        _logger.error("removeEmployeeFromDepartment: " + msg);
                        return msg;
                    }
                    employeeIds.remove(Integer.valueOf(employeeId));
                }
                ApiFuture<WriteResult> result = _db.collection("departments")
                        .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
                return result.get().toString();
            }
            String msg = "Department Not Found.";
            _logger.error("addEmployeeToDepartment: " + msg);
            return msg;
        }
        catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        _logger.info("Received message in EmployeeService: " + message);
    }
}
