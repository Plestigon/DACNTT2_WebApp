package tdtu.ems.operation_management_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Task;
import tdtu.ems.operation_management_service.models.TaskResult;
import tdtu.ems.operation_management_service.repositories.TaskRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository _taskRepository;
    private final Logger<TaskService> _logger;
    private final WebClient.Builder _webClient;

    public TaskService(TaskRepository taskRepository, WebClient.Builder webClient) {
        _taskRepository = taskRepository;
        _webClient = webClient;
        _logger = new Logger<>(TaskService.class);
    }

    @Override
    public Integer createTask(Task task) throws ExecutionException, InterruptedException {
        try {
            return _taskRepository.createTask(task);
        }
        catch (Exception e) {
            _logger.Error("createTask", e.getMessage());
            throw e;
        }
    }

    public List<TaskResult> getTasksByProjectId(int projectId) throws ExecutionException, InterruptedException {
        try {
            List<TaskResult> results = _taskRepository.getTasksByProjectId(projectId);
            results.sort(Comparator.comparing(TaskResult::getUpdateDate));
            return results;
        }
        catch (Exception e) {
            _logger.Error("getTasksByProjectId", e.getMessage());
            throw e;
        }
    }
}
