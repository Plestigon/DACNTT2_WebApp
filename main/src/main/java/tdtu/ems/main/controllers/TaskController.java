package tdtu.ems.main.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.utils.BaseResponse;
import tdtu.ems.main.utils.Enums;
import tdtu.ems.main.utils.SelectOptionsResult;
import tdtu.ems.main.models.operations.TaskDiscussionDto;
import tdtu.ems.main.models.operations.TaskDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {
    private final WebClient.Builder _webClient;

    public TaskController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("operations/projects/{projectId}/tasks")
    public ResponseEntity<BaseResponse> getTasks(@PathVariable int projectId, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/projects/" + projectId + "/tasks")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/tasks/{id}")
    public ResponseEntity<BaseResponse> getTask(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/tasks/" + id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/tasks")
    public ResponseEntity<BaseResponse> createTask(@RequestBody TaskDto task, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(task)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/tasks/edit")
    public ResponseEntity<BaseResponse> editTask(@RequestBody TaskDto task, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks/edit")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(task)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/tasks/{id}/state")
    public ResponseEntity<BaseResponse> updateTaskStateById(@PathVariable int id, @RequestParam int newValue, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks/" + id + "/state?newValue=" + newValue)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/tasks/{id}/priority")
    public ResponseEntity<BaseResponse> updateTaskPriorityById(@PathVariable int id, @RequestParam int newValue, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks/" + id + "/priority?newValue=" + newValue)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/tasks/priorities")
    public ResponseEntity<BaseResponse> getTaskPriorities() {
        var p = Enums.Priority.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < p.length; i++) {
            res.add(new SelectOptionsResult(p[i].name(), p[i].ordinal()));
        }
        return new ResponseEntity<>(new BaseResponse(res, 200, "OK"), HttpStatus.OK);
    }

    @GetMapping("operations/tasks/states")
    public ResponseEntity<BaseResponse> getTaskStates() {
        var p = Enums.TaskState.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < p.length; i++) {
            res.add(new SelectOptionsResult(p[i].name, p[i].ordinal()));
        }
        return new ResponseEntity<>(new BaseResponse(res, 200, "OK"), HttpStatus.OK);
    }

    @GetMapping("operations/tasks/{id}/members")
    public ResponseEntity<BaseResponse> getMembers(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/tasks/" + id + "/members")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/tasks/{id}/assign")
    public ResponseEntity<BaseResponse> assignTask(@PathVariable int id, @RequestParam int assigneeId, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks/" + id + "/assign?assigneeId=" + assigneeId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/my-project/{projectId}/tasks")
    public ResponseEntity<BaseResponse> getTasksFromMyProject(@PathVariable int projectId, @RequestParam int employeeId, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/my-project/" + projectId + "/tasks?employeeId=" + employeeId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/tasks/{id}/discussions")
    public ResponseEntity<BaseResponse> getDiscussions(@PathVariable int id, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://api-gateway/api/operations/tasks/" + id + "/discussions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/tasks/discussions")
    public ResponseEntity<BaseResponse> addDiscussion(@RequestBody TaskDiscussionDto entry, @RequestParam String token) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://api-gateway/api/operations/tasks/discussions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(entry)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(null, 500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
