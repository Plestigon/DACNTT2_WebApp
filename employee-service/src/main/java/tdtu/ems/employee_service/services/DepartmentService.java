package tdtu.ems.employee_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.employee_service.models.Department;
import tdtu.ems.employee_service.repositories.IDepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class DepartmentService implements IDepartmentService {
    private final IDepartmentRepository _departmentRepository;
    private final Logger<DepartmentService> _logger;

    public DepartmentService(IDepartmentRepository departmentRepository) {
        _departmentRepository = departmentRepository;
        _logger = new Logger<>(DepartmentService.class);
    }

    public String addDepartment(Department department) throws ExecutionException, InterruptedException {
        try {
            return _departmentRepository.addDepartment(department);
        }
        catch (Exception e) {
            _logger.Error("addDepartment", e.getMessage());
            throw e;
        }
    }

    public String removeDepartment(int id) throws ExecutionException, InterruptedException {
        try {
            return _departmentRepository.removeDepartment(id);
        }
        catch (Exception e) {
            _logger.Error("removeDepartment", e.getMessage());
            throw e;
        }
    }

    public List<Department> getDepartments() throws ExecutionException, InterruptedException {
        try {
            return _departmentRepository.getDepartments();
        }
        catch (Exception e) {
            _logger.Error("getDepartments", e.getMessage());
            throw e;
        }
    }

    public Department getDepartmentById(int id) throws ExecutionException, InterruptedException {
        try {
            return _departmentRepository.getDepartmentById(id);
        }
        catch (Exception e) {
            _logger.Error("getDepartmentByIds", e.getMessage());
            throw e;
        }
    }

    public Department getDepartmentByShortName(String shortName) throws ExecutionException, InterruptedException {
        try {
            return _departmentRepository.getDepartmentByShortName(shortName);
        }
        catch (Exception e) {
            _logger.Error("getDepartmentByShortName", e.getMessage());
            throw e;
        }
    }
}
