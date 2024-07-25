package tdtu.ems.employee_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu.ems.employee_service.utils.ChartData;
import tdtu.ems.employee_service.utils.Logger;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.repositories.EmployeeRepository;
import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.repositories.IDepartmentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository _employeeRepository;
    private final IDepartmentRepository _departmentRepository;
    private final Firestore _db;
    private final Logger<EmployeeService> _logger;

    public EmployeeService(EmployeeRepository employeeRepository, IDepartmentRepository departmentRepository) {
        _employeeRepository = employeeRepository;
        _departmentRepository = departmentRepository;
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(EmployeeService.class);
    }

    @Override
    public int addEmployee(Employee employee) throws ExecutionException, InterruptedException {
        try {
            Employee result = _employeeRepository.addEmployee(employee);
            if (result != null) {
                //Add new employee to department's employee list
                _departmentRepository.addEmployeeToDepartment(result.getId(), result.getDepartmentId());
                return result.getId();
            }
            return -1;
        } catch (Exception e) {
            _logger.Error("addEmployee", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeEmployee(int id) throws ExecutionException, InterruptedException {
        try {
            String result = _employeeRepository.removeEmployee(id);
            //Remove employee from department's employee list
            String result2 = _departmentRepository.removeEmployeeFromDepartments(id);
            return result;
        }
        catch (Exception e) {
            _logger.Error("removeEmployee", e.getMessage());
            throw e;
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
    public Employee getEmployeeByEmail(String email) throws ExecutionException, InterruptedException {
        try {
            Employee result = _employeeRepository.getEmployeeByEmail(email);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getEmployeeByEmail", e.getMessage());
            throw e;
        }
    }

    @Override
    public String changePassword(int id, String newPassword) throws ExecutionException, InterruptedException {
        try {
            return _employeeRepository.changePassword(id, newPassword);
        }
        catch (Exception e) {
            _logger.Error("changePassword", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ChartData> getChartData() throws ExecutionException, InterruptedException {
        try {
            return _employeeRepository.getChartData();
        }
        catch (Exception e) {
            _logger.Error("getChartData", e.getMessage());
            throw e;
        }
    }


}
