package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public String editProject(Project project) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(project.getId())).set(project);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProjectUpdate> getProjectUpdates(int projectId) {
        try {
            List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
            List<ProjectUpdate> projectUpdates = new ArrayList<>();
            for (int i : projectUpdateIds) {
                projectUpdates.add(getProjectUpdateById(i));
            }
            return projectUpdates;
        }
        catch (Exception e) {
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
