package tdtu.ems.department_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.department_service.models.Department;
import tdtu.ems.department_service.services.DepartmentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public BaseResponse getDepartments() throws ExecutionException, InterruptedException {
        List<Department> result = departmentService.getDepartments();
        if (result == null || result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new BaseResponse(result, 200, "OK");
    }

    @GetMapping("/departments/{id}")
    public Department getDepartmentById(@PathVariable int id) throws ExecutionException, InterruptedException {
        Department response = departmentService.getDepartmentById(id);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/departments")
    public String addDepartment(@RequestBody Department department) throws ExecutionException, InterruptedException {
        return departmentService.addDepartment(department);
    }

    @DeleteMapping("/departments/{id}")
    public String deleteDepartment(@PathVariable int id) throws ExecutionException, InterruptedException {
        return departmentService.removeDepartment(id);
    }
}
