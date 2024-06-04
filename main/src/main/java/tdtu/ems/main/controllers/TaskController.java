package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.main.models.SelectOptionsResult;
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

    @GetMapping("operations/project/{projectId}/tasks")
    public ResponseEntity<BaseResponse> getTasks(@PathVariable int projectId) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/project/" + projectId + "/tasks")
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/task/{id}")
    public ResponseEntity<BaseResponse> getTask(@PathVariable int id) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/task/" + id)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/task")
    public ResponseEntity<BaseResponse> createTask(@RequestBody TaskDto task) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task")
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

    @PostMapping("/operations/task/edit")
    public ResponseEntity<BaseResponse> editTask(@RequestBody TaskDto task) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task/edit")
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

    @PostMapping("/operations/task/{id}/state")
    public ResponseEntity<BaseResponse> updateTaskStateById(@PathVariable int id, @RequestParam int newValue) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task/" + id + "/state?newValue=" + newValue)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/task/{id}/priority")
    public ResponseEntity<BaseResponse> updateTaskPriorityById(@PathVariable int id, @RequestParam int newValue) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task/" + id + "/priority?newValue=" + newValue)
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

    @GetMapping("operations/task/{id}/members")
    public ResponseEntity<BaseResponse> getMembers(@PathVariable int id) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/task/" + id + "/members")
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/operations/task/{id}/assign")
    public ResponseEntity<BaseResponse> assignTask(@PathVariable int id, @RequestParam int assigneeId) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task/" + id + "/assign?assigneeId=" + assigneeId)
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
    public ResponseEntity<BaseResponse> getTasksFromMyProject(@PathVariable int projectId, @RequestParam int employeeId) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/my-project/" + projectId + "/tasks?employeeId=" + employeeId)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("operations/task/{id}/discussions")
    public ResponseEntity<BaseResponse> getDiscussions(@PathVariable int id) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/task/" + id + "/discussions")
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("operations/task/discussion")
    public ResponseEntity<BaseResponse> addDiscussion(@RequestBody TaskDiscussionDto entry) {
        try {
            BaseResponse result = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/task/discussion")
                    .bodyValue(entry)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
