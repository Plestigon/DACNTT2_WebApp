package tdtu.ems.employee_service.repository;

import tdtu.ems.main.models.Employee;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeRepository {
    List<Employee> getEmployees() throws ExecutionException, InterruptedException;
}
