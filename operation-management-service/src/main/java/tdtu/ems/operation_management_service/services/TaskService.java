package tdtu.ems.operation_management_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.*;
import tdtu.ems.operation_management_service.repositories.ProjectRepository;
import tdtu.ems.operation_management_service.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository _taskRepository;
    private final ProjectRepository _projectRepository;
    private final Logger<TaskService> _logger;
    private final WebClient.Builder _webClient;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, WebClient.Builder webClient) {
        _taskRepository = taskRepository;
        _projectRepository = projectRepository;
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

    @Override
    public TaskResult getTask(int id) throws ExecutionException, InterruptedException {
        try {
            return _taskRepository.getTask(id);
        }
        catch (Exception e) {
            _logger.Error("getTask", e.getMessage());
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

    @Override
    public List<TaskResult> getTasksFromMyProject(int projectId, int employeeId) throws ExecutionException, InterruptedException {
        try {
            List<TaskResult> result = _taskRepository.getTasksFromMyProject(projectId, employeeId);
            result.sort(Comparator.comparing(TaskResult::getUpdateDate));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getTasksFromMyProject", e.getMessage());
            throw e;
        }
    }

    @Override
    public String editTask(Task entry) throws ExecutionException, InterruptedException {
        try {
            String result = _taskRepository.editTask(entry);
            return result;
        }
        catch (Exception e) {
            _logger.Error("editTask", e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateTaskStateById(int id, int newState) throws ExecutionException, InterruptedException {
        try {
            String result = _taskRepository.updateTaskStateById(id, newState);
            return result;
        }
        catch (Exception e) {
            _logger.Error("updateTaskStateById", e.getMessage());
            throw e;
        }
    }

    @Override
    public String assignTask(int taskId, int employeeId) throws ExecutionException, InterruptedException {
        try {
            String result = _taskRepository.assignTask(taskId, employeeId);
            return result;
        }
        catch (Exception e) {
            _logger.Error("assignTask", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addDiscussion(TaskDiscussion d) throws ExecutionException, InterruptedException {
        try {
            Integer result = _taskRepository.addDiscussion(d);
            return result;
        }
        catch (Exception e) {
            _logger.Error("addDiscussion", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<TaskDiscussion> getDiscussions(int taskId) throws ExecutionException, InterruptedException {
        try {
            List<TaskDiscussion> result = _taskRepository.getDiscussions(taskId);
            result.sort(Comparator.comparing(TaskDiscussion::getCreateDate).reversed());
            return result;
        }
        catch (Exception e) {
            _logger.Error("getDiscussions", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProjectMemberResult> getMembersByTaskId(int id) throws ExecutionException, InterruptedException {
        try {
            List<Integer> memIds = new ArrayList<>();
            TaskResult task = _taskRepository.getTask(id);
            if (task != null) {
                Project prj = _projectRepository.getProjectById(task.getId());
                if (prj != null) {
                    memIds = prj.getMemberIds();
                }
            }
            List<ProjectMemberResult> result = _projectRepository.getProjectMembers(memIds);
            result.sort(Comparator.comparing(ProjectMemberResult::getJoinDate).reversed());
            return result;
        }
        catch (Exception e) {
            _logger.Error("getMembersByTaskId", e.getMessage());
            throw e;
        }
    }
}
