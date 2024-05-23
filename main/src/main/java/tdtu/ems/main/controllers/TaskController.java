package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.main.models.SelectOptionsResult;
import tdtu.ems.main.models.operations.TaskDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {
    private WebClient.Builder _webClient;

    public TaskController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("operations/tasks")
    public ResponseEntity<BaseResponse> getTasks(@RequestParam int projectId) {
        try {
            BaseResponse result = _webClient.build().get()
                    .uri("http://operation-management-service/api/operations/tasks?projectId=" + projectId)
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

    @GetMapping("operations/tasks/priorities")
    public ResponseEntity<BaseResponse> getTaskPriorities() {
        var p = Enums.Priority.values();
        List<SelectOptionsResult> res = new ArrayList<>();
        for(int i = 1; i < p.length; i++) {
            res.add(new SelectOptionsResult(p[i].name(), p[i].ordinal()));
        }
        return new ResponseEntity<>(new BaseResponse(res, 200, "OK"), HttpStatus.OK);
    }
}
