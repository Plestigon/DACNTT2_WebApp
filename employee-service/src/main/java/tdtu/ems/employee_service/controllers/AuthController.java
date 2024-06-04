package tdtu.ems.employee_service.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.LoginDto;
import tdtu.ems.employee_service.services.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final IEmployeeService _employeeService;

    public AuthController(EmployeeService employeeService) {
        _employeeService = employeeService;
    }

    @PostMapping("/auth/login")
    public BaseResponse login(@RequestBody LoginDto input) {
        try {
            Employee result = _employeeService.getEmployeeByEmail(input.getEmail());
            if (result == null) {
                return new BaseResponse(null, 404, "Not found");
            }
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
