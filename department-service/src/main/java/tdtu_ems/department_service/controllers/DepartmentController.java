package tdtu_ems.department_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tdtu_ems.main.models.Department;
import tdtu_ems.department_service.services.DepartmentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department/get")
    public List<Department> getDepartments() throws ExecutionException, InterruptedException {
        List<Department> response = departmentService.getDepartments();
        if (response == null || response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/department/get/{id}")
    public Department getDepartmentById(@PathVariable int id) throws ExecutionException, InterruptedException {
        Department response = departmentService.getDepartmentById(id);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/department/add")
    public String addDepartment(@RequestBody Department department) throws ExecutionException, InterruptedException {
        return departmentService.addDepartment(department);
    }

    @PostMapping("/department/remove")
    public String removeDepartment(@RequestParam int id) throws ExecutionException, InterruptedException {
        return departmentService.removeDepartment(id);
    }
}
