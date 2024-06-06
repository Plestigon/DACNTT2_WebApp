package tdtu.ems.employee_service.services;

import tdtu.ems.employee_service.models.Department;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDepartmentService {
    String addDepartment(Department department) throws ExecutionException, InterruptedException;
    String removeDepartment(int id) throws ExecutionException, InterruptedException;
    List<Department> getDepartments() throws ExecutionException, InterruptedException;
    Department getDepartmentById(int id) throws ExecutionException, InterruptedException;
    Department getDepartmentByShortName(String shortName) throws ExecutionException, InterruptedException;
}
