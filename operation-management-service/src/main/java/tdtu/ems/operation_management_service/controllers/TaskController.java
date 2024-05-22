package tdtu.ems.operation_management_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.operation_management_service.models.Task;
import tdtu.ems.operation_management_service.models.TaskDiscussion;
import tdtu.ems.operation_management_service.models.TaskResult;
import tdtu.ems.operation_management_service.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService _taskService;

    public TaskController(TaskService taskService) {
        _taskService = taskService;
    }

    @PostMapping("/operations/task")
    public BaseResponse createTask(@RequestBody Task task) {
        try {
            //Task task = new Task(0, "test2", 2, 2, null, null, 1, 0, "Description2", new ArrayList<>());
            Integer result = _taskService.createTask(task);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/operations/tasks")
    public BaseResponse getTasksByProjectId(@RequestParam int projectId) {
        try {
            List<TaskResult> result = _taskService.getTasksByProjectId(projectId);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/task/{id}")
    public BaseResponse updateTaskStateById(@PathVariable int id, @RequestParam int newState) {
        try {
            String result = _taskService.updateTaskStateById(id, newState);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/task/{id}/assign")
    public BaseResponse assignTask(@PathVariable int id, @RequestParam int assigneeId) {
        try {
            String result = _taskService.assignTask(id, assigneeId);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/operations/task/discussion")
    public BaseResponse addDiscussion(@RequestBody TaskDiscussion d) {
        try {
            //TaskDiscussion dd = new TaskDiscussion(0, 1, 1, "tva", "a@a", "Dev", null, "hello");
            Integer result = _taskService.addDiscussion(d);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/operations/task/{id}/discussions")
    public BaseResponse getDiscussions(@PathVariable int id) {
        try {
            List<TaskDiscussion> result = _taskService.getDiscussions(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
