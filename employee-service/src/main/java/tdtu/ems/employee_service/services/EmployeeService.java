package tdtu.ems.employee_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.repositories.EmployeeRepository;
import tdtu.ems.employee_service.models.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository _employeeRepository;
    private final Firestore _db;
    private final Logger<EmployeeService> _logger;

    public EmployeeService(EmployeeRepository employeeRepository) {
        _employeeRepository = employeeRepository;
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(EmployeeService.class);
    }

    @Override
    public int addEmployee(Employee employee) throws ExecutionException, InterruptedException {
        try {
            return _employeeRepository.addEmployee(employee);
        } catch (Exception e) {
            _logger.Error("addEmployee", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeEmployee(int id) {
        try {
            Employee employee = getEmployeeById(id);
            if (employee == null) {
                String msg = "Employee not found";
                _logger.Error("removeEmployee: ", msg);
                return msg;
            }
            ApiFuture<WriteResult> result = _db.collection("employees").document(String.valueOf(id)).delete();
            //Remove employee from department's employee list
            //_logger.info("removeEmployeeFromDepartment: " + removeEmployeeFromDepartment(id, employee.getDepartmentId()));
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmployeeResult> getEmployees(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<EmployeeResult> result = _employeeRepository.getEmployees(ids);
            result.sort(Comparator.comparing(EmployeeResult::getId));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getEmployees", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EmployeeResult> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<EmployeeResult> res = _employeeRepository.getEmployeesExcept(ids);
            res.sort(Comparator.comparing(EmployeeResult::getId));
            return res;
        }
        catch (Exception e) {
            _logger.Error("getEmployeesExcept", e.getMessage());
            throw e;
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

    @Override
    public ProjectUpdateEmployeeDataResult getProjectUpdateEmployeeData(int writerId, List<Integer> checkIds) {
        return _employeeRepository.getProjectUpdateEmployeeData(writerId, checkIds);
    }

//    public List<Employee> getEmployeesByDepartment(String shortName) throws ExecutionException, InterruptedException {
//        Department department = departmentService.getDepartmentByShortName(shortName);
//        if (department == null || department.getEmployeeIds() == null) {
//            logger.error("getemployeesByDepartment: " + "Department not found");
//            return null;
//        }
//        return getEmployeesByIds(department.getEmployeeIds());
//    }

//    @Override
//    public Department getDepartmentById(int id) {
//        try {
//            DocumentSnapshot data = _db.collection("departments").document(String.valueOf(id)).get().get();
//            Department department = null;
//            if (data.exists()) {
//                department = data.toObject(Department.class);
//            }
//            return department;
//        }
//        catch (Exception e) {
//            return null;
//        }
//    }

//    @Override
//    public String addEmployeeToDepartment(int employeeId, int departmentId) {
//        try {
//            Department department = getDepartmentById(departmentId);
//            if (department != null) {
//                List<Integer> employeeIds = new ArrayList<>();
//                if (department.getEmployeeIds() != null) {
//                    employeeIds = department.getEmployeeIds();
//                    if (employeeIds.contains(employeeId)) {
//                        String msg = "This employeeId already exist in this Department.";
//                        _logger.error("addEmployeeToDepartment: " + msg);
//                        return msg;
//                    }
//                }
//                employeeIds.add(employeeId);
//                ApiFuture<WriteResult> result = _db.collection("departments")
//                        .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
//                return result.get().toString();
//            }
//            String msg = "Department Not Found.";
//            _logger.error("addEmployeeToDepartment: " + msg);
//            return msg;
//        }
//        catch (Exception e) {
//            return null;
//        }
//    }

//    @Override
//    public String removeEmployeeFromDepartment(int employeeId, int departmentId) {
//        try {
//            Department department = getDepartmentById(departmentId);
//            if (department != null) {
//                List<Integer> employeeIds = new ArrayList<>();
//                if (department.getEmployeeIds() != null) {
//                    employeeIds = department.getEmployeeIds();
//                    if (!employeeIds.contains(employeeId)) {
//                        String msg = "This employeeId does not exist in this Department.";
//                        _logger.error("removeEmployeeFromDepartment: " + msg);
//                        return msg;
//                    }
//                    employeeIds.remove(Integer.valueOf(employeeId));
//                }
//                ApiFuture<WriteResult> result = _db.collection("departments")
//                        .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
//                return result.get().toString();
//            }
//            String msg = "Department Not Found.";
//            _logger.error("addEmployeeToDepartment: " + msg);
//            return msg;
//        }
//        catch (Exception e) {
//            return null;
//        }
//    }

//    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
//    public void consume(String message) {
//        _logger.info("Received message in EmployeeService: " + message);
//    }
}
