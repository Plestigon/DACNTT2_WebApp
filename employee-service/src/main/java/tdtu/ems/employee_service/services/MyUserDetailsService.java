package tdtu.ems.employee_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tdtu.ems.employee_service.models.Employee;
import tdtu.ems.employee_service.models.MyUserDetails;
import tdtu.ems.employee_service.repositories.EmployeeRepository;

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository _employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Employee e = _employeeRepository.getEmployeeByEmail(username);
            return new MyUserDetails(e);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid email");
        }
    }
}
