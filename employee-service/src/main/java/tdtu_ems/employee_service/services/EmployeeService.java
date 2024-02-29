package tdtu_ems.employee_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tdtu_ems.main.models.Department;
import tdtu_ems.main.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService {
    private final Firestore db;
    private final Logger logger;

    public EmployeeService() {
        db = FirestoreClient.getFirestore();
        logger = LoggerFactory.getLogger(EmployeeService.class);
    }

    public String addEmployee(Employee employee) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = db.collection("employees");
        //region Check if email already used
        QuerySnapshot query = employeesDb.whereEqualTo("email", employee.getEmail()).get().get();
        if (!query.getDocuments().isEmpty()) {
            String msg = "This email has already been used!";
            logger.error("addEmployee: " + msg);
            return msg;
        }
        //endregion
        DocumentReference idTracerDoc = db.collection("idTracer").document("employees");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        employee.setId((int) id);
        ApiFuture<WriteResult> result = employeesDb.document(String.valueOf(id)).set(employee);
        ApiFuture<WriteResult> resultUpdId = idTracerDoc.update("id", id);
        logger.info("addEmployee update idTracer: " + resultUpdId.get().getUpdateTime());
        //Add employee to department's employee list
        logger.info("addEmployeeToDepartment: " + addEmployeeToDepartment((int) id, employee.getDepartmentId()));
        return result.get().getUpdateTime().toString();
    }

    public String removeEmployee(int id) throws ExecutionException, InterruptedException {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            String msg = "Employee not found";
            logger.error("removeEmployee: " + msg);
            return msg;
        }
        ApiFuture<WriteResult> result = db.collection("employees").document(String.valueOf(id)).delete();
        //Remove employee from department's employee list
        logger.info("removeEmployeeFromDepartment: " + removeEmployeeFromDepartment(id, employee.getDepartmentId()));
        return result.get().getUpdateTime().toString();
    }

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = db.collection("employees");
        List<Employee> employees = new ArrayList<>();
        for (DocumentSnapshot data : employeesDb.get().get().getDocuments()) {
            employees.add(data.toObject(Employee.class));
        }
        return employees;
    }

    public Employee getEmployeeById(int id) throws ExecutionException, InterruptedException {
        DocumentSnapshot data = db.collection("employees").document(String.valueOf(id)).get().get();
        Employee employee = null;
        if (data.exists()) {
            employee = data.toObject(Employee.class);
        }
        return employee;
    }

    public List<Employee> getEmployeesByIds(List<Integer> employeeIds) throws ExecutionException, InterruptedException {
        if (employeeIds.isEmpty()) {
            return null;
        }
        QuerySnapshot query = db.collection("employees")
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

    public Employee getEmployeeByEmail(String email) throws ExecutionException, InterruptedException {
        QuerySnapshot query = db.collection("employees")
                .whereEqualTo("email", email).get().get();
        List<QueryDocumentSnapshot> documents = query.getDocuments();
        return documents.isEmpty() ? null : documents.get(0).toObject(Employee.class);
    }

//    public List<Employee> getEmployeesByDepartment(String shortName) throws ExecutionException, InterruptedException {
//        Department department = departmentService.getDepartmentByShortName(shortName);
//        if (department == null || department.getEmployeeIds() == null) {
//            logger.error("getemployeesByDepartment: " + "Department not found");
//            return null;
//        }
//        return getEmployeesByIds(department.getEmployeeIds());
//    }

    public Department getDepartmentById(int id) throws ExecutionException, InterruptedException {
        DocumentSnapshot data = db.collection("departments").document(String.valueOf(id)).get().get();
        Department department = null;
        if (data.exists()) {
            department = data.toObject(Department.class);
        }
        return department;
    }

    public String addEmployeeToDepartment(int employeeId, int departmentId) throws ExecutionException, InterruptedException {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            List<Integer> employeeIds = new ArrayList<>();
            if (department.getEmployeeIds() != null) {
                employeeIds = department.getEmployeeIds();
                if (employeeIds.contains(employeeId)) {
                    String msg = "This employeeId already exist in this Department.";
                    logger.error("addEmployeeToDepartment: " + msg);
                    return msg;
                }
            }
            employeeIds.add(employeeId);
            ApiFuture<WriteResult> result = db.collection("departments")
                    .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
            return result.get().toString();
        }
        String msg = "Department Not Found.";
        logger.error("addEmployeeToDepartment: " + msg);
        return msg;
    }

    public String removeEmployeeFromDepartment(int employeeId, int departmentId) throws ExecutionException, InterruptedException {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            List<Integer> employeeIds = new ArrayList<>();
            if (department.getEmployeeIds() != null) {
                employeeIds = department.getEmployeeIds();
                if (!employeeIds.contains(employeeId)) {
                    String msg = "This employeeId does not exist in this Department.";
                    logger.error("removeEmployeeFromDepartment: " + msg);
                    return msg;
                }
                employeeIds.remove(Integer.valueOf(employeeId));
            }
            ApiFuture<WriteResult> result = db.collection("departments")
                    .document(String.valueOf(departmentId)).update("employeeIds", employeeIds);
            return result.get().toString();
        }
        String msg = "Department Not Found.";
        logger.error("addEmployeeToDepartment: " + msg);
        return msg;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        logger.info("Received message in EmployeeService: " + message);
    }
}
