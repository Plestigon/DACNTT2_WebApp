package tdtu.ems.employee_service.repositories;

import tdtu.ems.employee_service.models.Employee;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeRepository {
    List<Employee> getEmployees() throws ExecutionException, InterruptedException;
}
