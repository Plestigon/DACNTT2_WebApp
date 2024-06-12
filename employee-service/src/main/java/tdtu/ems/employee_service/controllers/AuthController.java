package tdtu.ems.employee_service.controllers;

import com.google.auth.oauth2.UserCredentials;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.employee_service.utils.BaseResponse;
import tdtu.ems.employee_service.models.ChangePasswordDto;
import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.LoginDto;
import tdtu.ems.employee_service.models.LoginUser;
import tdtu.ems.employee_service.services.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService _authService;
    private final AuthenticationManager _authenticationManager;
    private final EmployeeService _employeeService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, EmployeeService employeeService) {
        _authService = authService;
        _authenticationManager = authenticationManager;
        _employeeService = employeeService;
    }

    @PostMapping("/auth/token")
    public BaseResponse getToken(@RequestBody LoginDto loginDto) {
        try {
            Authentication auth = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            if (auth.isAuthenticated()) {
                String token = _authService.generateToken(loginDto.getEmail());
                return new BaseResponse(token, 200, "OK");
            }
            return new BaseResponse(null, 401, "Not authenticated");
        }
        catch (AuthenticationException ae) {
            return new BaseResponse(null, 400, "Incorrect email or password");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/auth/validate")
    public BaseResponse validateToken(@RequestParam String token) {
        try {
            Claims claims = _authService.validateToken(token);
            return new BaseResponse(claims, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/auth/user")
    public BaseResponse getUser(@RequestParam String email) {
        try {
            Employee result = _employeeService.getEmployeeByEmail(email);
            if (result == null) {
                return new BaseResponse(null, 404, "Not found");
            }
            return new BaseResponse(new LoginUser(result), 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PutMapping("/auth/change-password")
    public BaseResponse changePassword(@RequestBody ChangePasswordDto input) {
        try {
            Employee employee = _employeeService.getEmployeeById(input.getUserId());
            if (employee == null) {
                return new BaseResponse(null, 404, "User not found");
            }
            if (!BCrypt.checkpw(input.getOldPass(), employee.getPassword())) {
                return new BaseResponse(null, 400, "Incorrect password");
            }
            String result = _employeeService.changePassword(input.getUserId(), input.getNewPass());
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
