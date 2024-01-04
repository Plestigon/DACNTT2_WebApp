//package tdtu_ems.userservice.services;
//
//import org.springframework.stereotype.Service;
//import tdtu_ems.userservice.models.Department;
//import tdtu_ems.userservice.models.User;
//import tdtu_ems.userservice.repositories.DepartmentRepository;
//
//import java.util.List;
//
//@Service
//public class DepartmentService {
//    private final DepartmentRepository repository;
//
//    public DepartmentService(DepartmentRepository repository) {
//        this.repository = repository;
//    }
//
//    public Department addDepartment(Department department) {
//        return repository.save(department);
//    }
//
//    public List<Department> getDepartments() {
//        return repository.findAll();
//    }
//
//    public Department getDepartmentById(String id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    public Department getDepartmentByShortName(String shortName) {
//        return repository.findByShortName(shortName).orElse(null);
//    }
//
//    public List<User> getUsersByDepartment(String shortName) {
//        return getDepartmentByShortName(shortName).getUsers();
//    }
//}
