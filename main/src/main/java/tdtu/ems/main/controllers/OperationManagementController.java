package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.utils.BaseResponse;
import tdtu.ems.main.utils.Enums;
import tdtu.ems.main.utils.SelectOptionsResult;
import tdtu.ems.main.models.operations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/")
public class OperationManagementController {
    private final String OPERATIONS_PAGE_TITLE = "Operations Management";
    private final String PROJECT_PAGE_TITLE = "Project";
    private final WebClient.Builder _webClient;

    public OperationManagementController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("operations/projects/{id}")
    public ResponseEntity<ProjectResult> getProject(@PathVariable int id, @RequestParam String token) {
        try {
            ProjectResult res = null;
            res = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/projects/" + id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ProjectResult.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/projects")
    public ResponseEntity<BaseResponse> getProjects(@RequestParam(required = false) Integer employeeId, @RequestParam String token) {
        try {

            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/projects" +
                            (employeeId != null ? "?employeeId=" + employeeId : ""))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/employees")
    public ResponseEntity<BaseResponse> getEmployees(@RequestParam(required = false) List<Integer> ids, @RequestParam String token) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/employees" + query)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    public ResponseEntity<BaseResponse> getEmployeesToAdd(@RequestParam(required = false) List<Integer> ids, @RequestParam String token) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "/except?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/employees" + query)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/projects")
    public ResponseEntity<BaseResponse> createProject(@RequestBody ProjectCreateDto project, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/projects")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(project)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/projects/edit")
    public ResponseEntity<BaseResponse> editProject(@RequestBody ProjectEditDto project, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/projects/edit")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(project)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("operations/projects/{id}")
    public ResponseEntity<BaseResponse> deleteProject(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().delete()
                    .uri("http://api-gateway/api/operations/projects/" + id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/projects/statuses")
    public ResponseEntity<List<SelectOptionsResult>> getProjectStatuses() {
        var statuses = Enums.ProjectStatus.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < statuses.length; i++) {
            res.add(new SelectOptionsResult(statuses[i].name, statuses[i].ordinal()));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("operations/projects/roles")
    public ResponseEntity<BaseResponse> getProjectRoles() {
        var roles = Enums.ProjectRole.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < roles.length; i++) {
            res.add(new SelectOptionsResult(roles[i].name(), roles[i].ordinal()));
        }
        return new ResponseEntity<>(new BaseResponse(res, 200, "OK"), HttpStatus.OK);
    }

    @PostMapping("operations/projects/{id}/update")
    public ResponseEntity<BaseResponse> updateProjectStatus(@PathVariable int id, @RequestParam int status, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/projects/" + id + "/update?status=" + status)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/projects/{id}/updates")
    public ResponseEntity<BaseResponse> getProjectUpdates(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/projects/" + id + "/updates")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/projects/{id}/updates")
    public ResponseEntity<BaseResponse> addProjectUpdates(@RequestBody ProjectUpdateDto input, @PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/projects/" + id + "/updates")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(input)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/projects/members")
    public ResponseEntity<BaseResponse> getProjectMembers(@RequestParam List<Integer> ids, @RequestParam String token) {
        try {
            String query = "";
            if (ids != null && !ids.isEmpty()) {
                query = "?ids=" + ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/projects/members" + query)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/projects/{projectId}/member")
    public ResponseEntity<BaseResponse> addMember(@PathVariable int projectId, @RequestParam int memberId, @RequestParam int role, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/projects/" + projectId +
                            "/member?memberId=" + memberId + "&role=" + role)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("operations/projects/{projectId}/member")
    public ResponseEntity<BaseResponse> removeMember(@PathVariable int projectId, @RequestParam int memberId, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().delete()
                    .uri("http://api-gateway/api/operations/projects/" + projectId + "/member?memberId=" + memberId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    public ResponseEntity<BaseResponse> getMyProjects(@RequestParam int employeeId, @RequestParam String token) {
        try {
            BaseResponse res = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/my-projects?employeeId=" + employeeId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
