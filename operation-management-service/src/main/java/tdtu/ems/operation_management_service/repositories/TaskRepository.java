package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Repository;
import tdtu.ems.operation_management_service.utils.Enums;
import tdtu.ems.operation_management_service.utils.Logger;
import tdtu.ems.operation_management_service.models.*;

import java.util.*;
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

    @Override
    public TaskResult getTask(int id) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeesDb = _db.collection("employees");
        CollectionReference projectsDb = _db.collection("projects");
        Task task = tasksDb.document(String.valueOf(id)).get().get().toObject(Task.class);
        if (task == null) {
            throw new IllegalArgumentException("Task not found");
        }
        String assigneeName = employeesDb.document(String.valueOf(task.getAssigneeId())).get().get().getString("name");
        String projectName = projectsDb.document(String.valueOf(task.getProjectId())).get().get().getString("name");
        TaskResult result = new TaskResult(task, assigneeName);
        result.setProjectName(projectName);
        return result;
    }

    public List<TaskResult> getTasksByProjectId(int projectId) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeeDb = _db.collection("employees");
        List<TaskResult> result = new ArrayList<>();
        for (DocumentSnapshot data : tasksDb.get().get().getDocuments()) {
            Task task = data.toObject(Task.class);
            if (task != null && task.getProjectId() == projectId) {
                String assigneeName = employeeDb.document(String.valueOf(task.getAssigneeId())).get().get().getString("name");
                result.add(new TaskResult(task, assigneeName));
            }
        }
        return result;
    }

    @Override
    public String editTask(Task entry) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        Task existing = tasksDb.document(String.valueOf(entry.getId())).get().get().toObject(Task.class);
        if (existing == null) {
            throw new IllegalArgumentException("Wrong task id");
        }
        existing.setName(entry.getName());
        existing.setDueDate(entry.getDueDate());
        existing.setDescription(entry.getDescription());
        ApiFuture<WriteResult> result = tasksDb.document(String.valueOf(entry.getId())).set(existing);
        return result.get().getUpdateTime().toString();
    }

    public String updateTaskStateById(int id, int newValue) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        Task t = tasksDb.document(String.valueOf(id)).get().get().toObject(Task.class);
        if (t == null) {
            throw new IllegalArgumentException("Task not found");
        }
        t.setState(newValue);
        t.setUpdateDate(new Date());
        ApiFuture<WriteResult> result = tasksDb.document(String.valueOf(id)).set(t);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String updateTaskPriorityById(int id, int newValue) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        Task t = tasksDb.document(String.valueOf(id)).get().get().toObject(Task.class);
        if (t == null) {
            throw new IllegalArgumentException("Task not found");
        }
        t.setPriority(newValue);
        ApiFuture<WriteResult> result = tasksDb.document(String.valueOf(id)).set(t);
        return result.get().getUpdateTime().toString();
    }

    public String assignTask(int taskId, int employeeId) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeeDb = _db.collection("employees");
        var t = tasksDb.document(String.valueOf(taskId)).get().get();
        if (!t.exists()) {
            throw new IllegalArgumentException("Task not found");
        }
        var e = employeeDb.document(String.valueOf(employeeId)).get().get();
        if (!e.exists()) {
            throw new IllegalArgumentException("Employee not found");
        }
        ApiFuture<WriteResult> result = tasksDb.document(String.valueOf(taskId)).update("assigneeId", employeeId);
        return result.get().getUpdateTime().toString();
    }

    public Integer addDiscussion(TaskDiscussion dis) throws ExecutionException, InterruptedException {
        CollectionReference taskDiscussionsDb = _db.collection("taskDiscussions");
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeesDb = _db.collection("employees");
        int taskId = dis.getTaskId();
        Task task = tasksDb.document(String.valueOf(taskId)).get().get().toObject(Task.class);
        if (task == null) {
            throw new IllegalArgumentException("Task not found");
        }

        DocumentReference idTracer = _db.collection("idTracer").document("taskDiscussions");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        dis.setId((int) id);
        dis.setCreateDate(new Date());
        var emp = employeesDb.document(String.valueOf(dis.getWriterId())).get().get();
        dis.setWriterName(emp.getString("name"));
        dis.setWriterEmail(emp.getString("email"));
        Long role = emp.getLong("role");
        if (role != null) {
            dis.setWriterRole(Enums.Role.values()[role.intValue()].name);
        }
        ApiFuture<WriteResult> result = taskDiscussionsDb.document(String.valueOf(id)).set(dis);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);

        if (task.getDiscussions() == null) {
            task.setDiscussions(new ArrayList<>());
        }
        if (!task.getDiscussions().contains((int) id)) {
            task.getDiscussions().add((int) id);
        }
        task.setUpdateDate(new Date());
        ApiFuture<WriteResult> result2 = tasksDb.document(String.valueOf(taskId)).set(task);
        return (int) id;
    }

    public List<TaskDiscussion> getDiscussions(int taskId) throws ExecutionException, InterruptedException {
        CollectionReference taskDiscussionsDb = _db.collection("taskDiscussions");
        List<TaskDiscussion> result = new ArrayList<>();
        for (var data : taskDiscussionsDb.get().get().getDocuments()) {
            if (data.exists()) {
                TaskDiscussion dis = data.toObject(TaskDiscussion.class);
                if (dis.getTaskId() == taskId) {
                    result.add(dis);
                }
            }
        }
        return result;
    }

    public List<TaskResult> getTasksFromMyProject(int projectId, int employeeId) throws ExecutionException, InterruptedException {
        CollectionReference tasksDb = _db.collection("tasks");
        CollectionReference employeeDb = _db.collection("employees");
        CollectionReference projectsDb = _db.collection("projects");
        List<TaskResult> result = new ArrayList<>();
        for (DocumentSnapshot data : tasksDb.get().get().getDocuments()) {
            Task task = data.toObject(Task.class);
            if (task != null && task.getProjectId() == projectId && task.getAssigneeId() == employeeId) {
                if (task.getState() == Enums.TaskState.ToDo.ordinal() ||
                    task.getState() == Enums.TaskState.InProgress.ordinal()) {
                    String name = employeeDb.document(String.valueOf(task.getAssigneeId())).get().get().getString("name");
                    result.add(new TaskResult(task, name));
                }
            }
        }
        return result;
    }
}
