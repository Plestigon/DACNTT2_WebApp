//package tdtu_ems.userservice.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import tdtu_ems.userservice.models.Department;
//import tdtu_ems.userservice.models.User;
//import tdtu_ems.userservice.services.DepartmentService;
//
//import java.util.List;
//
//@RestController
//public class DepartmentController {
//    private final DepartmentService departmentService;
//
//    @Autowired
//    public DepartmentController(DepartmentService departmentService) {
//        this.departmentService = departmentService;
//    }
//
//    @GetMapping("/departments")
//    public List<Department> getDepartments() {
//        return departmentService.getDepartments();
//    }
//
//    @GetMapping("/department/{shortName}")
//    public Department getDepartmentByShortName(@PathVariable String shortName) {
//        return departmentService.getDepartmentByShortName(shortName);
//    }
//
//    @GetMapping("/department/id/{id}")
//    public Department getDepartmentById(@PathVariable String id) {
//        return departmentService.getDepartmentById(id);
//    }
//
//    @GetMapping("/department/{id}/users")
//    public List<User> getUsersByDepartment(@PathVariable String id) {
//        return departmentService.getUsersByDepartment(id);
//    }
//}
