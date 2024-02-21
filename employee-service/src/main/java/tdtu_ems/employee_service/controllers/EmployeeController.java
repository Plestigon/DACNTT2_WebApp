package tdtu_ems.employee_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tdtu_ems.main.models.Employee;
import tdtu_ems.employee_service.services.EmployeeService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        List<Employee> employees = null;
        employees = employeeService.getEmployees();
        if (employees == null || employees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return employees;
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable int id) throws ExecutionException, InterruptedException {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return employee;
    }

    @PostMapping("/add-employee")
    public String addEmployee(@RequestBody Employee employee) throws ExecutionException, InterruptedException {
        return employeeService.addEmployee(employee);
    }

    @PostMapping("/remove-employee")
    public String removeEmployee(@RequestParam int id) throws ExecutionException, InterruptedException {
        return employeeService.removeEmployee(id);
    }
}
