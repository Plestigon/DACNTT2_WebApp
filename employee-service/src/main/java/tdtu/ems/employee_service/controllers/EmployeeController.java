package tdtu.ems.employee_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.employee_service.utils.*;
import tdtu.ems.employee_service.models.EmployeeResult;
import tdtu.ems.employee_service.models.ProjectUpdateEmployeeDataResult;
import tdtu.ems.employee_service.services.EmployeeService;
import tdtu.ems.employee_service.services.IEmployeeService;
import tdtu.ems.employee_service.models.Employee;

import java.util.ArrayList;
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
        try {
            List<EmployeeResult> result = _employeeService.getEmployees(ids);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/paged")
    public PagedResponse getEmployeesPaged(@RequestParam int page) {
        try {
            PagedResponse result = _employeeService.getEmployeesPaged(page);
            return result;
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
        }
    }

    @GetMapping("/employees/except")
    public BaseResponse getEmployeesExcept(@RequestParam List<Integer> ids) {
        try {
            List<EmployeeResult> result = _employeeService.getEmployeesExcept(ids);
            return new BaseResponse(result, 200, "OK");
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
    public BaseResponse removeEmployee(@PathVariable int id) {
        try {
            String result = _employeeService.removeEmployee(id);
            return new BaseResponse(result, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/approvers")
    public BaseResponse getApprovers(@RequestParam int userId) {
        try {
            List<EmployeeResult> employees = _employeeService.getEmployees(null);
            Employee user = _employeeService.getEmployeeById(userId);
            List<SelectOptionsResult> result = new ArrayList<>();
            for (EmployeeResult e : employees) {
                if (e.getId() != userId && e.getRole() >= user.getRole()) {
                    result.add(new SelectOptionsResult(e.getName(), e.getId()));
                }
            }
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/chart-data")
    public BaseResponse getChartData() {
        try {
            List<ChartData> result = _employeeService.getChartData();
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
