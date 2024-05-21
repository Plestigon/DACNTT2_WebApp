package tdtu.ems.operation_management_service.repositories;

import tdtu.ems.operation_management_service.models.Task;
import tdtu.ems.operation_management_service.models.TaskResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ITaskRepository {
    Integer createTask(Task entry) throws ExecutionException, InterruptedException;
    List<TaskResult> getTasksByProjectId(int projectId) throws ExecutionException, InterruptedException;
}
