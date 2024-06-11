package tdtu.ems.employee_service.repositories;

import tdtu.ems.employee_service.models.Department;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDepartmentRepository {
    String addDepartment(Department department) throws ExecutionException, InterruptedException;
    String removeDepartment(int id) throws ExecutionException, InterruptedException;
    List<Department> getDepartments() throws ExecutionException, InterruptedException;
    Department getDepartmentById(int id) throws ExecutionException, InterruptedException;
    Department getDepartmentByShortName(String shortName) throws ExecutionException, InterruptedException;
    String addEmployeeToDepartment(int employeeId, int departmentId) throws ExecutionException, InterruptedException;
    String removeEmployeeFromDepartments(int employeeId) throws ExecutionException, InterruptedException;
}
