package tdtu.ems.operation_management_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.repositories.ProjectRepository;

import java.util.Comparator;
import java.util.List;

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
    public List<ProjectResult> getProjects() {
        List<ProjectResult> result = _projectRepository.getProjectResults();
        result.sort(Comparator.comparing(ProjectResult::getId));
        return result;
    }

    @Override
    public ProjectResult getProjectById(int id) {
        ProjectResult result =  _projectRepository.getProjectResultById(id);
        return result;
    }

    @Override
    public String addProject(Project project) {
        project.setStatus(1);
        return _projectRepository.addProject(project);
    }

    @Override
    public String removeProject(int id) {
        return _projectRepository.removeProject(id);
    }

    @Override
    public BaseResponse editProject(Project project) {
        return _projectRepository.editProject(project);
    }

    @Override
    public String updateProjectStatus(int id, int status) {
        String result = _projectRepository.updateProjectStatus(id, status);
        return result;
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
