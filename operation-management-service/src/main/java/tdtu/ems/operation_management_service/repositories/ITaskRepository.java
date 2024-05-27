package tdtu.ems.operation_management_service.repositories;

import tdtu.ems.operation_management_service.models.Task;
import tdtu.ems.operation_management_service.models.TaskDiscussion;
import tdtu.ems.operation_management_service.models.TaskResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ITaskRepository {
    Integer createTask(Task entry) throws ExecutionException, InterruptedException;
    TaskResult getTask(int id) throws ExecutionException, InterruptedException;
    List<TaskResult> getTasksByProjectId(int projectId) throws ExecutionException, InterruptedException;
    String  editTask(Task entry) throws ExecutionException, InterruptedException;
    String updateTaskStateById(int id, int newValue) throws ExecutionException, InterruptedException;
    String updateTaskPriorityById(int id, int newValue) throws ExecutionException, InterruptedException;
    String assignTask(int taskId, int employeeId) throws ExecutionException, InterruptedException;
    Integer addDiscussion(TaskDiscussion d) throws ExecutionException, InterruptedException;
    List<TaskDiscussion> getDiscussions(int taskId) throws ExecutionException, InterruptedException;
    List<TaskResult> getTasksFromMyProject(int projectId, int employeeId) throws ExecutionException, InterruptedException;
}
