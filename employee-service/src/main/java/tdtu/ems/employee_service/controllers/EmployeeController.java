package tdtu.ems.employee_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.employee_service.models.EmployeeResult;
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
    public BaseResponse getEmployees(@RequestParam(required = false) List<Integer> ids) {
        List<EmployeeResult> employees = null;
        try {
            employees = _employeeService.getEmployees(ids);
            return new BaseResponse(employees, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/except")
    public BaseResponse getEmployeesExcept(@RequestParam List<Integer> ids) {
        List<EmployeeResult> employees = null;
        try {
            employees = _employeeService.getEmployeesExcept(ids);
            return new BaseResponse(employees, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/{id}")
    public BaseResponse getEmployeeById(@PathVariable int id) {
        try {
            Employee employee = _employeeService.getEmployeeById(id);
            return new BaseResponse(employee, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/by-email")
    public BaseResponse getEmployeeByEmail(@RequestParam String email) {
        try {
            Employee employee = _employeeService.getEmployeeByEmail(email);
            return new BaseResponse(employee, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/employees")
    public BaseResponse addEmployee(@RequestBody Employee employee) {
        try {
            int id = _employeeService.addEmployee(employee);
            if (id == -1) {
                return new BaseResponse(null, 400, "Email has already been used.");
            }
            return new BaseResponse(id, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
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
