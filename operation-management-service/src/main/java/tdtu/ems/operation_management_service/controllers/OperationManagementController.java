package tdtu.ems.operation_management_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.main.models.Project;
import tdtu.ems.main.models.ProjectUpdate;
import tdtu.ems.operation_management_service.services.ProjectService;

import java.util.List;

@RestController
public class OperationManagementController {
    private final ProjectService projectService;

    public OperationManagementController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("operations/projects/get")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> response = null;
        try {
            response = projectService.getProjects();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("operations/projects/add")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        String response = null;
        try {
            response = projectService.addProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("operations/projects/remove")
    public ResponseEntity<String> removeProject(@RequestParam int id) {
        String response = null;
        try {
            response = projectService.removeProject(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("operations/projects/edit")
    public ResponseEntity<String> editProject(@RequestBody Project project) {
        String response = null;
        try {
            response = projectService.editProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("operations/projects/update/get")
    public ResponseEntity<List<ProjectUpdate>> getProjectUpdates(@RequestParam int projectId) {
        List<ProjectUpdate> response = null;
        try {
            response = projectService.getProjectUpdates(projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("operations/projects/update/add")
    public ResponseEntity<String> addProjectUpdate(@RequestBody ProjectUpdate projectUpdate, @RequestParam int projectId) {
        String response = null;
        try {
            response = projectService.addProjectUpdate(projectUpdate, projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
