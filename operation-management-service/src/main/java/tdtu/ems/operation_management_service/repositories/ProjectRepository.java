package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.models.ProjectUpdateResult;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class ProjectRepository implements IProjectRepository {
    private final Firestore _db;
    private final Logger<ProjectRepository> _logger;

    public ProjectRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ProjectRepository.class);
    }

    @Override
    public List<Project> getProjects() {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            List<Project> projects = new ArrayList<>();
            for (DocumentSnapshot data : projectsDb.get().get().getDocuments()) {
                projects.add(data.toObject(Project.class));
            }
            return projects;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProjectResult> getProjectResults() {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            CollectionReference employeesDb = _db.collection("employees");
            List<ProjectResult> projects = new ArrayList<>();
            for (DocumentSnapshot data : projectsDb.get().get().getDocuments()) {
                Project prj = data.toObject(Project.class);
                if (prj != null) {
                    DocumentSnapshot ownerData = employeesDb.document(String.valueOf(prj.getOwnerId())).get().get();
                    ProjectResult prjRes = new ProjectResult(prj, ownerData.getString("name"));
                    projects.add(prjRes);
                }
            }
            return projects;
        }
        catch (Exception e) {
            _logger.Error("getProjectsWithData", e.getMessage());
            return null;
        }
    }

    @Override
    public Project getProjectById(int id) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
            Project project = null;
            if (data.exists()) {
                project = data.toObject(Project.class);
            }
            return project;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProjectResult getProjectResultById(int id) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            CollectionReference employeesDb = _db.collection("employees");
            DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
            if (data.exists()) {
                Project project = data.toObject(Project.class);
                if (project != null) {
                    DocumentSnapshot ownerData = employeesDb.document(String.valueOf(project.getOwnerId())).get().get();
                    ProjectResult result = new ProjectResult(project, ownerData.getString("name"));
                    return result;
                }
                _logger.Error("getProjectResultById", "Project is null.");
                return null;
            }
            _logger.Error("getProjectResultById", "Data does not exist.");
            return null;
        }
        catch (Exception e) {
            _logger.Error("getProjectResultById", e.getMessage());
            return null;
        }
    }

    @Override
    public String addProject(Project project) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            DocumentReference idTracer = _db.collection("idTracer").document("projects");
            long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            project.setId((int) id);
            ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).set(project);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String removeProject(int id) throws ExecutionException, InterruptedException {
        Project project = getProjectById(id);
        if (project == null) {
            throw new NotFoundException();
        }
        ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String editProject(Project entry) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        Project existing = projectsDb.document(String.valueOf(entry.getId())).get().get().toObject(Project.class);
        if (existing == null) {
            throw new NotFoundException();
        }
        existing.setName(entry.getName());
        existing.setDueDate(entry.getDueDate());
        existing.setDescription(entry.getDescription());
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(entry.getId())).set(existing);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).update("status", status);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        Project prj = getProjectById(projectId);
        if (prj == null) {
            _logger.Error("getProjectUpdates", "Project not found. id: " + projectId);
            throw new NotFoundException();
        }
        List<Integer> projectUpdateIds = prj.getProjectUpdateIds();
        List<ProjectUpdateResult> res = new ArrayList<>();
        if (projectUpdateIds != null) {
            for (int i : projectUpdateIds) {
                ProjectUpdate pu = getProjectUpdateById(i);
                if (pu == null) continue;
                String writerName = "<SYSTEM>";
                if (pu.getWriterId() > 0) {
                    writerName = employeesDb.document(String.valueOf(pu.getWriterId())).get().get().getString("name");
                }
                res.add(new ProjectUpdateResult(pu, writerName));
            }
        }
        return res;
    }

    @Override
    public ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projectUpdates");
        DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
        ProjectUpdate projectUpdate = null;
        if (data.exists()) {
            projectUpdate = data.toObject(ProjectUpdate.class);
        }
        return projectUpdate;
    }

    @Override
    public int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectUpdatesDb = _db.collection("projectUpdates");
        DocumentReference idTracer = _db.collection("idTracer").document("projectUpdates");
        long projectUpdateId = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        projectUpdate.setId((int) projectUpdateId);
        projectUpdate.setCreateTime(new Date());
        ApiFuture<WriteResult> result = projectUpdatesDb.document(String.valueOf(projectUpdateId)).set(projectUpdate);

        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", projectUpdateId);

        return (int)projectUpdateId;
    }

    @Override
    public String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException {
        List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
        if (projectUpdateIds == null) {
            projectUpdateIds = new ArrayList<>();
        }
        projectUpdateIds.add(projectUpdateId);
        ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(projectId))
                .update("projectUpdateIds", projectUpdateIds);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String addMemberToProject(int memberId, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        Project prj = projectsDb.document(String.valueOf(projectId)).get().get().toObject(Project.class);
        if (prj == null) {
            throw new NotFoundException();
        }
        if (!prj.getMemberIds().contains(memberId)) {
            prj.getMemberIds().add(memberId);
        }
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(projectId)).set(prj);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String removeMemberFromProject(int memberId, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        Project prj = projectsDb.document(String.valueOf(projectId)).get().get().toObject(Project.class);
        if (prj == null) {
            throw new NotFoundException();
        }
        if (prj.getMemberIds().contains(memberId)) {
            prj.getMemberIds().remove(Integer.valueOf(memberId));
        }
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(projectId)).set(prj);
        return result.get().getUpdateTime().toString();
    }
}
