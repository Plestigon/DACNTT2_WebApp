package tdtu.ems.operation_management_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import tdtu.ems.main.general.Logger;
import tdtu.ems.main.models.Project;
import tdtu.ems.main.models.ProjectUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ProjectService {
    private final Firestore db;
    private final Logger<ProjectService> _logger;

    public ProjectService() {
        db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ProjectService.class);
    }

    public List<Project> getProjects() throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = db.collection("projects");
        List<Project> projects = new ArrayList<>();
        for (DocumentSnapshot data : projectsDb.get().get().getDocuments()) {
            projects.add(data.toObject(Project.class));
        }
        _logger.Error("getProjects", "test");
        return projects;
    }

    public Project getProjectById(int id) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = db.collection("projects");
        DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
        Project project = null;
        if (data.exists()) {
            project = data.toObject(Project.class);
        }
        return project;
    }

    public String addProject(Project project) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = db.collection("projects");
        DocumentReference idTracer = db.collection("idTracer").document("projects");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        project.setId((int) id);
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).set(project);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return result.get().getUpdateTime().toString();
    }

    public String removeProject(int id) throws ExecutionException, InterruptedException {
        Project project = getProjectById(id);
        if (project == null) {
            return null;
        }
        ApiFuture<WriteResult> result = db.collection("projects").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    public String editProject(Project project) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = db.collection("projects");
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(project.getId())).set(project);
        return result.get().getUpdateTime().toString();
    }

    public List<ProjectUpdate> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException {
        List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
        List<ProjectUpdate> projectUpdates = new ArrayList<>();
        for (int i : projectUpdateIds) {
            projectUpdates.add(getProjectUpdateById(i));
        }
        return projectUpdates;
    }

    public ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = db.collection("projectUpdates");
        DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
        ProjectUpdate projectUpdate = null;
        if (data.exists()) {
            projectUpdate = data.toObject(ProjectUpdate.class);
        }
        return projectUpdate;
    }

    public String addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectUpdatesDb = db.collection("projectUpdates");
        DocumentReference idTracer = db.collection("idTracer").document("projectUpdates");
        long projectUpdateId = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        projectUpdate.setId((int) projectUpdateId);
        ApiFuture<WriteResult> result = projectUpdatesDb.document(String.valueOf(projectUpdateId)).set(projectUpdate);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", projectUpdateId);
        //Add projectUpdate to project
        _logger.Info("addProjectUpdateToProject",
                addProjectUpdateToProject((int) projectUpdateId, projectId));
        return result.get().getUpdateTime().toString();
    }

    public String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException {
        List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
        projectUpdateIds.add(projectUpdateId);
        ApiFuture<WriteResult> result = db.collection("projects").document(String.valueOf(projectId))
                .update("projectUpdateIds", projectUpdateIds);
        return result.get().getUpdateTime().toString();
    }
}
