package tdtu.ems.employee_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.SelectOptionsResult;
import tdtu.ems.employee_service.models.Department;
import tdtu.ems.employee_service.services.IDepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    private final IDepartmentService _departmentService;

    @Autowired
    public DepartmentController(IDepartmentService departmentService) {
        _departmentService = departmentService;
    }

    @GetMapping("/employees/departments")
    public BaseResponse getDepartments() {
        try {
            List<Department> result = _departmentService.getDepartments();
            if (result == null || result.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/departments/{id}")
    public BaseResponse getDepartmentById(@PathVariable int id) {
        try {
            Department result = _departmentService.getDepartmentById(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/employees/departments")
    public BaseResponse addDepartment(@RequestBody Department department) throws ExecutionException, InterruptedException {
        try {
            String result = _departmentService.addDepartment(department);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/employees/departments/{id}")
    public BaseResponse deleteDepartment(@PathVariable int id) throws ExecutionException, InterruptedException {
        try {
            String result = _departmentService.removeDepartment(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/employees/departments/options")
    public BaseResponse getDepartmentsOptions() throws ExecutionException, InterruptedException {
        try {
            List<Department> result = _departmentService.getDepartments();
            List<SelectOptionsResult> options = new ArrayList<>();
            for (Department d : result) {
                options.add(new SelectOptionsResult(d.getShortName(), d.getId()));
            }
            return new BaseResponse(options, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
