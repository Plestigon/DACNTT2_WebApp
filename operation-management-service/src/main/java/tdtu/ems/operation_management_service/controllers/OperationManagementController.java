package tdtu.ems.operation_management_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.models.Project;
import tdtu.ems.main.models.ProjectUpdate;
import tdtu.ems.operation_management_service.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationManagementController {
    private final ProjectService projectService;
    private final WebClient.Builder webClientBuilder;

    public OperationManagementController(ProjectService projectService, WebClient.Builder webClientBuilder) {
        this.projectService = projectService;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/projects/get")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> response = null;
        try {
            response = projectService.getProjects();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/projects/add")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        String response = null;
        try {
            response = projectService.addProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/projects/remove")
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

    @PostMapping("/projects/edit")
    public ResponseEntity<String> editProject(@RequestBody Project project) {
        String response = null;
        try {
            response = projectService.editProject(project);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/projects/update/get")
    public ResponseEntity<List<ProjectUpdate>> getProjectUpdates(@RequestParam int projectId) {
        List<ProjectUpdate> response = null;
        try {
            response = projectService.getProjectUpdates(projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/projects/update/add")
    public ResponseEntity<String> addProjectUpdate(@RequestBody ProjectUpdate projectUpdate, @RequestParam int projectId) {
        String response = null;
        try {
            response = projectService.addProjectUpdate(projectUpdate, projectId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        String res = null;
        try {
            res = webClientBuilder.build().get()
                    .uri("http://employee-service/api/employees/get")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
