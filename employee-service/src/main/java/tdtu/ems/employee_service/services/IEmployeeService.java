package tdtu.ems.employee_service.services;

import tdtu.ems.employee_service.models.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);
    String removeEmployee(int id);
    List<Employee> getEmployees();
    Employee getEmployeeById(int id);
    List<Employee> getEmployeesByIds(List<Integer> employeeIds);
    Employee getEmployeeByEmail(String email);
//    Department getDepartmentById(int id);
//    String addEmployeeToDepartment(int employeeId, int departmentId);
//    String removeEmployeeFromDepartment(int employeeId, int departmentId);
}
