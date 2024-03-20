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
    private final Logger<ProjectService> _logger;
    private final WebClient.Builder _webClient;

    public ProjectService(ProjectRepository projectRepository, WebClient.Builder webClient) {
        _projectRepository = projectRepository;
        _webClient = webClient;
        _logger = new Logger<>(ProjectService.class);
    }

    @Override
    public List<Project> getProjects() {
        List<Project> result = _projectRepository.getProjects();
        return result;
    }

    @Override
    public Project getProjectById(int id) {
        Project result =  _projectRepository.getProjectById(id);
        return result;
    }

    @Override
    public String addProject(Project project) {
        return _projectRepository.addProject(project);
    }

    @Override
    public String removeProject(int id) {
        return _projectRepository.removeProject(id);
    }

    @Override
    public String editProject(Project project) {
        return _projectRepository.editProject(project);
    }

    @Override
    public List<ProjectUpdate> getProjectUpdates(int projectId) {
        return _projectRepository.getProjectUpdates(projectId);
    }

    @Override
    public ProjectUpdate getProjectUpdateById(int id) {
        return _projectRepository.getProjectUpdateById(id);
    }

    @Override
    public String addProjectUpdate(ProjectUpdate projectUpdate, int projectId) {
        return _projectRepository.addProjectUpdate(projectUpdate, projectId);
    }

    @Override
    public String addProjectUpdateToProject(int projectUpdateId, int projectId) {
        return _projectRepository.addProjectUpdateToProject(projectUpdateId, projectId);
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
