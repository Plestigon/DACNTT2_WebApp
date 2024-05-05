package tdtu.ems.employee_service.services;

import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);
    String removeEmployee(int id);
    List<Employee> getEmployees(List<Integer> ids);
    List<Employee> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException;
    Employee getEmployeeById(int id);
    Employee getEmployeeByEmail(String email);
    ProjectUpdateEmployeeDataResult getProjectUpdateEmployeeData(int writerId, List<Integer> checkIds);
}
