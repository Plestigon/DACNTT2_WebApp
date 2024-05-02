package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
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
    public String removeProject(int id) {
        try {
            Project project = getProjectById(id);
            if (project == null) {
                return null;
            }
            ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(id)).delete();
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public BaseResponse editProject(Project entry) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            Project existing = projectsDb.document(String.valueOf(entry.getId())).get().get().toObject(Project.class);
            if (existing != null) {
                existing.setName(entry.getName());
                existing.setDueDate(entry.getDueDate());
                existing.setDescription(entry.getDescription());
                ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(entry.getId())).set(existing);
                return new BaseResponse(null, 200, result.get().getUpdateTime().toString());
            }
            return new BaseResponse(null, 404, "Project not found");
        }
        catch (Exception e) {
            _logger.Error("editProject", e.getMessage());
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @Override
    public String updateProjectStatus(int id, int status) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).update("status", status);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            _logger.Error("updateProjectStatus", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProjectUpdateResult> getProjectUpdates(int projectId) {
        CollectionReference employeesDb = _db.collection("employees");
        try {
            List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
            List<ProjectUpdateResult> res = new ArrayList<>();
            for (int i : projectUpdateIds) {
                ProjectUpdate pu = getProjectUpdateById(i);
                String writerName = "<SYSTEM>";
                if (pu.getWriterId() > 0) {
                    writerName = employeesDb.document(String.valueOf(pu.getWriterId())).get().get().getString("name");
                }
                res.add(new ProjectUpdateResult(pu, writerName));
            }
            return res;
        }
        catch (Exception e) {
            _logger.Error("getProjectUpdates", e.getMessage());
            return null;
        }
    }

    @Override
    public ProjectUpdate getProjectUpdateById(int id) {
        try {
            CollectionReference projectsDb = _db.collection("projectUpdates");
            DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
            ProjectUpdate projectUpdate = null;
            if (data.exists()) {
                projectUpdate = data.toObject(ProjectUpdate.class);
            }
            return projectUpdate;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String addProjectUpdate(ProjectUpdate projectUpdate, int projectId) {
        try {
            CollectionReference projectUpdatesDb = _db.collection("projectUpdates");
            DocumentReference idTracer = _db.collection("idTracer").document("projectUpdates");
            long projectUpdateId = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            projectUpdate.setId((int) projectUpdateId);
            ApiFuture<WriteResult> result = projectUpdatesDb.document(String.valueOf(projectUpdateId)).set(projectUpdate);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", projectUpdateId);
            //Add projectUpdate to project
            _logger.Info("addProjectUpdateToProject",
                    addProjectUpdateToProject((int) projectUpdateId, projectId));
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String addProjectUpdateToProject(int projectUpdateId, int projectId) {
        try {
            List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
            projectUpdateIds.add(projectUpdateId);
            ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(projectId))
                    .update("projectUpdateIds", projectUpdateIds);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}
