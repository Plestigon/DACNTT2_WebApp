package tdtu.ems.employee_service.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdtu.ems.employee_service.utils.Logger;
import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.MyUserDetails;
import tdtu.ems.employee_service.repositories.EmployeeRepository;

import java.util.concurrent.ExecutionException;

@Service
public class AuthService {
    private final EmployeeRepository _employeeRepository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtService _jwtService;
    private final Logger<AuthService> _logger;

    public AuthService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        _employeeRepository = employeeRepository;
        _passwordEncoder = passwordEncoder;
        _jwtService = jwtService;
        _logger = new Logger<>(AuthService.class);
    }

    public String generateToken(String email) throws ExecutionException, InterruptedException {
        try {
            Employee e = _employeeRepository.getEmployeeByEmail(email);
            MyUserDetails user = new MyUserDetails(e);
            return _jwtService.generateToken(user);
        }
        catch (Exception e) {
            _logger.Error("generateToken", e.getMessage());
            throw e;
        }
    }

    public Claims validateToken(String token) {
        try {
            return _jwtService.validateToken(token);
        }
        catch (Exception e) {
            _logger.Error("validateToken", e.getMessage());
            throw e;
        }
    }
}
