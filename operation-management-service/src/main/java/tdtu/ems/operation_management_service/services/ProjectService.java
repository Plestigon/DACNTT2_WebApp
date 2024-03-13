package tdtu.ems.operation_management_service.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProjectService implements IProjectService {
    private final ProjectRepository _projectRepository;
    private final Firestore _db;
    private final Logger<ProjectService> _logger;
    private final WebClient.Builder _webClient;

    public ProjectService(ProjectRepository projectRepository, WebClient.Builder webClient) {
        _projectRepository = projectRepository;
        _webClient = webClient;
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ProjectService.class);
    }

    @Override
    public List<Project> getProjects() {
        List<Project> projects = _projectRepository.getProjects();
        if (projects == null || projects.isEmpty()) {
            return null;
        }
        return null;
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

    @Override
    public Object test() {
        Object res = null;
        res = _webClient.build().get()
                .uri("http://employee-service/api/employees")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        return res;
    }
}
