package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class TaskRepository implements ITaskRepository {
    private final Firestore _db;
    private final Logger<TaskRepository> _logger;

    public TaskRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(TaskRepository.class);
    }

    public Integer createTask(Task task) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        DocumentReference idTracer = _db.collection("idTracer").document("tasks");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        task.setId((int) id);
        task.setCreateDate(new Date());
        task.setUpdateDate(new Date());
        task.setState(Enums.TaskState.ToDo.ordinal());
        ApiFuture<WriteResult> result = tasksDb.document(String.valueOf(id)).set(task);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return (int) id;
    }

    public List<TaskResult> getTasksByProjectId(int projectId) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeeDb = _db.collection("employees");
        List<TaskResult> result = new ArrayList<>();
        for (DocumentSnapshot data : tasksDb.get().get().getDocuments()) {
            Task task = data.toObject(Task.class);
            if (task != null && task.getProjectId() == projectId) {
                String name = employeeDb.document(String.valueOf(task.getAssigneeId())).get().get().getString("name");
                result.add(new TaskResult(task, name));
            }
        }
        return result;
    }
}
