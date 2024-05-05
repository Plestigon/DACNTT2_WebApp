package tdtu.ems.employee_service.repositories;

import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeRepository {
    List<Employee> getEmployees(List<Integer> ids);
    List<Employee> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException;
    ProjectUpdateEmployeeDataResult getProjectUpdateEmployeeData(int writerId, List<Integer> checkIds);
}
