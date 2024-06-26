package tdtu.ems.employee_service.services;

import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeService {
    int addEmployee(Employee employee) throws ExecutionException, InterruptedException;
    String removeEmployee(int id) throws ExecutionException, InterruptedException;
    List<EmployeeResult> getEmployees(List<Integer> ids) throws ExecutionException, InterruptedException;
    List<EmployeeResult> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException;
    Employee getEmployeeById(int id);
    Employee getEmployeeByEmail(String email) throws ExecutionException, InterruptedException;
    String changePassword(int id, String newPassword) throws ExecutionException, InterruptedException;
}
