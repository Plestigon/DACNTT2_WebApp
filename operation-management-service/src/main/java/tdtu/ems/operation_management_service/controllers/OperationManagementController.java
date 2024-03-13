package tdtu.ems.operation_management_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationManagementController {
    private final ProjectService _projectService;

    public OperationManagementController(ProjectService projectService) {
        _projectService = projectService;
    }

    @GetMapping("/operations/projects")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> response = null;
        try {
            response = _projectService.getProjects();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/operations/projects")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        String response = null;
        try {
            response = _projectService.addProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/operations/projects/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable int id) {
        String response = null;
        try {
            response = _projectService.removeProject(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/operations/projects")
    public ResponseEntity<String> editProject(@RequestBody Project project) {
        String response = null;
        try {
            response = _projectService.editProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/operations/projects/updates")
    public ResponseEntity<List<ProjectUpdate>> getProjectUpdates(@RequestParam int projectId) {
        List<ProjectUpdate> response = null;
        try {
            response = _projectService.getProjectUpdates(projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/operations/projects/updates")
    public ResponseEntity<String> addProjectUpdate(@RequestBody ProjectUpdate projectUpdate, @RequestParam int projectId) {
        String response = null;
        try {
            response = _projectService.addProjectUpdate(projectUpdate, projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public Object test() {
        Object res = null;
        try {
            res = _projectService.test();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
