package tdtu.ems.employee_service.repositories;

import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeRepository {
    List<EmployeeResult> getEmployees(List<Integer> ids) throws ExecutionException, InterruptedException;
    List<EmployeeResult> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException;
    ProjectUpdateEmployeeDataResult getProjectUpdateEmployeeData(int writerId, List<Integer> checkIds);
}
