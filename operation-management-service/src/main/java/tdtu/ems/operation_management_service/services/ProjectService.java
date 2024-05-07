package tdtu.ems.operation_management_service.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.core_service.models.Enums;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.models.ProjectUpdateResult;
import tdtu.ems.operation_management_service.repositories.ProjectRepository;

import java.util.Comparator;
import java.util.List;
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
        project.setStatus(0);
        return _projectRepository.addProject(project);
    }

    @Override
    public String removeProject(int id) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.removeProject(id);
        }
        catch (Exception e) {
            _logger.Error("removeProject", e.getMessage());
            throw e;
        }
    }

    @Override
    public String editProject(Project project) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.editProject(project);
        }
        catch (Exception e) {
            _logger.Error("editProject", e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException {
        try {
            Project prj = _projectRepository.getProjectById(id);
            if (prj.getStatus() == status) {
                return "Target status is the same as current status";
            }
            String res = _projectRepository.updateProjectStatus(id, status);
            ProjectUpdate pu = new ProjectUpdate(0, "Project status changed to \"" + Enums.ProjectStatus.values()[status].name + "\"");
            addProjectUpdate(pu, id);
            return res;
        }
        catch (Exception e) {
            _logger.Error("updateProjectStatus", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException {
        try {
            List<ProjectUpdateResult> res = _projectRepository.getProjectUpdates(projectId);
            res.sort(Comparator.comparing(ProjectUpdateResult::getCreateTime).reversed());
            return res;
        }
        catch (Exception e) {
            _logger.Error("getProjectUpdates", e.getMessage());
            throw e;
        }
    }

    @Override
    public ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.getProjectUpdateById(id);
        }
        catch (Exception e) {
            _logger.Error("getProjectUpdateById", e.getMessage());
            throw e;
        }
    }

    @Override
    public int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException {
        try {
            int res = _projectRepository.addProjectUpdate(projectUpdate, projectId);
            //Add projectUpdate to project
            String addRes = addProjectUpdateToProject(res, projectId);
            return res;
        }
        catch (Exception e) {
            _logger.Error("addProjectUpdate", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException {
        try {
            String res = _projectRepository.addProjectUpdateToProject(projectUpdateId, projectId);
            return res;
        }
        catch (Exception e) {
            _logger.Error("addProjectUpdateToProject", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addMemberToProject(int memberId, int projectId) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.addMemberToProject(memberId, projectId);
        }
        catch (Exception e) {
            _logger.Error("addMemberToProject", e.getMessage());
            return null;
        }
    }

    @Override
    public String removeMemberFromProject(int memberId, int projectId) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.removeMemberFromProject(memberId, projectId);
        }
        catch (Exception e) {
            _logger.Error("removeMemberFromProject", e.getMessage());
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
