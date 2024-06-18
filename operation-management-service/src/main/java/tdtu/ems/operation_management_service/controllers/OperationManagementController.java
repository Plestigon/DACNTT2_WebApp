package tdtu.ems.operation_management_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.operation_management_service.utils.BaseResponse;
import tdtu.ems.operation_management_service.models.*;
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
    public BaseResponse getProjects(@RequestParam(required = false) Integer employeeId) {
        try {
            List<ProjectResult> response = _projectService.getProjects(employeeId);
            return new BaseResponse(response, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/operations/projects")
    public ResponseEntity<ProjectResult> getProjectById(@RequestParam int id) {
        ProjectResult response = null;
        try {
            response = _projectService.getProjectById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/operations/my-projects")
    public BaseResponse getMyProjects(@RequestParam int employeeId) {
        try {
            List<MyProjectResult> response = _projectService.getMyProjects(employeeId);
            return new BaseResponse(response, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/projects")
    public BaseResponse addProject(@RequestBody Project project) {
        try {
            String result = _projectService.addProject(project);
            return new BaseResponse(result, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/operations/projects/{id}")
    public BaseResponse deleteProject(@PathVariable int id) {
        try {
            String result = _projectService.removeProject(id);
            return new BaseResponse(result, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/projects/edit")
    public BaseResponse editProject(@RequestBody Project project) {
        try {
            String response = _projectService.editProject(project);
            return new BaseResponse(null, 200,"OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/projects/{id}/update")
    public BaseResponse updateProjectStatus(@PathVariable int id, @RequestParam int status) {
        try {
            String response = _projectService.updateProjectStatus(id, status);
            return new BaseResponse(null, 200,"OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/operations/projects/updates/{projectId}")
    public BaseResponse getProjectUpdates(@PathVariable int projectId) {
        List<ProjectUpdateResult> response = null;
        try {
            response = _projectService.getProjectUpdates(projectId);
            return new BaseResponse(response, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/projects/updates")
    public BaseResponse addProjectUpdate(@RequestBody ProjectUpdate projectUpdate, @RequestParam int projectId) {
        try {
            int response = _projectService.addProjectUpdate(projectUpdate, projectId);
            return new BaseResponse(null, 200,"OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/operations/projects/members")
    public BaseResponse getProjectMembers(@RequestParam List<Integer> ids) {
        try {
            List<ProjectMemberResult> result = _projectService.getProjectMembers(ids);
            return new BaseResponse(result, 200, "OK");
        } catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("operations/projects/{projectId}/member")
    public BaseResponse addMember(@PathVariable int projectId, @RequestParam int memberId, @RequestParam int role) {
        try {
            String res = _projectService.addMemberToProject(memberId, projectId, role);
            return new BaseResponse(null, 200,"OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("operations/projects/{projectId}/member")
    public BaseResponse removeMember(@PathVariable int projectId, @RequestParam int memberId) {
        try {
            String res = _projectService.removeMemberFromProject(memberId, projectId);
            return new BaseResponse(null, 200,"OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
