package tdtu.ems.employee_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.services.EmployeeService;
import tdtu.ems.employee_service.services.IEmployeeService;
import tdtu.ems.employee_service.models.Employee;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final IEmployeeService _employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        _employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(required = false) List<Integer> ids) {
        List<Employee> employees = null;
        try {
            employees = _employeeService.getEmployees(ids);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/except")
    public ResponseEntity<List<Employee>> getEmployeesExcept(@RequestParam List<Integer> ids) {
        List<Employee> employees = null;
        try {
            employees = _employeeService.getEmployeesExcept(ids);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        try {
            Employee employee = _employeeService.getEmployeeById(id);
            if (employee == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            Employee added = _employeeService.addEmployee(employee);
            if (added == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employees/{id}")
    public String removeEmployee(@PathVariable int id) {
        try {
            String result = _employeeService.removeEmployee(id);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/employees/project-update-data")
    public ResponseEntity<ProjectUpdateEmployeeDataResult> getProjectUpdateEmployeeData(@RequestParam int writerId, @RequestParam List<Integer> checkedIds) {
        try {
            ProjectUpdateEmployeeDataResult res = _employeeService.getProjectUpdateEmployeeData(writerId, checkedIds);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
