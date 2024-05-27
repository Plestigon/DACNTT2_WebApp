package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.main.models.*;
import tdtu.ems.main.models.operations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class OperationManagementController {
    private final String OPERATIONS_PAGE_TITLE = "Operations Management";
    private final String PROJECT_PAGE_TITLE = "Project";
    private WebClient.Builder _webClient;

    public OperationManagementController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("operations/index")
    public String index(Model model) {
        model.addAttribute("title", OPERATIONS_PAGE_TITLE);
        return "Operations/Operations";
    }

    @GetMapping("operations/project")
    @ResponseBody
    public ResponseEntity<ProjectResult> getProject(@RequestParam int id) {
        try {
            ProjectResult res = null;
            res = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/project?id=" + id)
                    .retrieve()
                    .bodyToMono(ProjectResult.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "operations/projects", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseResponse> getProjects(@RequestParam(required = false) Integer employeeId) {
        try {
            BaseResponse res = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/projects" +
                            (employeeId != null ? "?employeeId=" + employeeId : ""))
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/employees")
    @ResponseBody
    public ResponseEntity<BaseResponse> getEmployees(@RequestParam(required = false) List<Integer> ids) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://employee-service/api/employees" + query)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/employees/to-add")
    @ResponseBody
    public ResponseEntity<BaseResponse> getEmployeesToAdd(@RequestParam(required = false) List<Integer> ids) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "/except?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://employee-service/api/employees" + query)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "operations/project", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createProject(@RequestBody ProjectCreateDto project) {
        String res = null;
        try {
            res = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/projects")
                    .bodyValue(project)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("operations/project/edit")
    @ResponseBody
    public ResponseEntity<BaseResponse> editProject(@RequestBody ProjectEditDto project) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/project/edit")
                    .bodyValue(project)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "operations/project", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteProject(@RequestParam("id") String id) {
        String res = null;
        try {
            res = _webClient.build().delete()
                    .uri("http://operation-management-service/api/operations/projects/" + id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("operations/project/statuses")
    @ResponseBody
    public ResponseEntity<List<SelectOptionsResult>> getProjectStatuses() {
        var statuses = Enums.ProjectStatus.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < statuses.length; i++) {
            res.add(new SelectOptionsResult(statuses[i].name, statuses[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("operations/project/roles")
    @ResponseBody
    public ResponseEntity<BaseResponse> getProjectRoles() {
        var roles = Enums.ProjectRole.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < roles.length; i++) {
            res.add(new SelectOptionsResult(roles[i].name(), roles[i].ordinal()));
        }
        return new ResponseEntity<>(new BaseResponse(res, 200, "OK"), HttpStatus.OK);
    }

    @PostMapping("operations/project/{id}/update")
    @ResponseBody
    public ResponseEntity<BaseResponse> updateProjectStatus(@PathVariable int id, @RequestParam int status) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/project/" + id + "/update?status=" + status)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/project/updates/{projectId}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getProjectUpdates(@PathVariable int projectId) {
        try {
            BaseResponse res = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/project/updates/" + projectId)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/project/members")
    @ResponseBody
    public ResponseEntity<BaseResponse> getProjectMembers(@RequestParam List<Integer> ids) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/project/members" + query)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/project/{projectId}/member")
    @ResponseBody
    public ResponseEntity<BaseResponse> addMember(@PathVariable int projectId, @RequestParam int memberId, @RequestParam int role) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/project/" + projectId +
                            "/member?memberId=" + memberId + "&role=" + role)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("operations/project/{projectId}/member")
    @ResponseBody
    public ResponseEntity<BaseResponse> removeMember(@PathVariable int projectId, @RequestParam int memberId) {
        try {
            BaseResponse res = _webClient.build().delete()
                    .uri("http://operation-management-service/api/operations/project/" + projectId + "/member?memberId=" + memberId)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/my-projects")
    @ResponseBody
    public ResponseEntity<BaseResponse> getMyProjects(@RequestParam int employeeId) {
        try {
            BaseResponse res = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/my-projects?employeeId=" + employeeId)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
