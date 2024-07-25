package tdtu.ems.operation_management_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.operation_management_service.utils.Enums;
import tdtu.ems.operation_management_service.utils.Logger;
import tdtu.ems.operation_management_service.models.*;
import tdtu.ems.operation_management_service.repositories.ProjectRepository;
import tdtu.ems.operation_management_service.repositories.TaskRepository;
import tdtu.ems.operation_management_service.utils.PagedResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProjectService implements IProjectService {
    private final ProjectRepository _projectRepository;
    private final TaskRepository _taskRepository;
    private final Logger<ProjectService> _logger;
    private final WebClient.Builder _webClient;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, WebClient.Builder webClient) {
        _projectRepository = projectRepository;
        _taskRepository = taskRepository;
        _webClient = webClient;
        _logger = new Logger<>(ProjectService.class);
    }

    @Override
    public PagedResponse getProjects(int page, String search, Integer status) throws ExecutionException, InterruptedException {
        try {
            PagedResponse result = _projectRepository.getProjects(page, search, status);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getProjects", e.getMessage());
            throw e;
        }
    }

    @Override
    public ProjectResult getProjectById(int id) {
        ProjectResult result =  _projectRepository.getProjectResultById(id);
        return result;
    }

    public List<MyProjectResult> getMyProjects(int employeeId) throws ExecutionException, InterruptedException {
        try {
            List<MyProjectResult> result = new ArrayList<>();
            List<ProjectResult> prs = _projectRepository.getProjectsByEmployeeId(employeeId);
            for (ProjectResult pr : prs) {
                List<ProjectMemberResult> members = _projectRepository.getProjectMembers(pr.getMemberIds());
                //Find member data that belongs to this employee
                ProjectMemberResult memberInfo = members.stream().filter(m -> m.getEmployeeId() == employeeId).findFirst().orElse(null);
                if (memberInfo != null) {
                    List<TaskResult> tasks = _taskRepository.getTasksByProjectId(memberInfo.getProjectId());
                    int inProgressCnt = 0;
                    int notStartedCnt = 0;
                    Date nearestDueDate = new Date(Long.MAX_VALUE);
                    for (TaskResult t : tasks) {
                        if (t.getAssigneeId() == memberInfo.getId()) {
                            if (t.getState() == Enums.TaskState.InProgress.ordinal()) {
                                inProgressCnt++;
                            }
                            else if (t.getState() == Enums.TaskState.ToDo.ordinal()) {
                                notStartedCnt++;
                            }
                            if (t.getDueDate() != null && t.getDueDate().before(nearestDueDate) && t.getDueDate().after(new Date())) {
                                nearestDueDate = t.getDueDate();
                            }
                        }
                    }
                    if (nearestDueDate.compareTo(new Date(Long.MAX_VALUE)) == 0) {
                        nearestDueDate = null;
                    }
                    result.add(new MyProjectResult(pr, memberInfo, inProgressCnt, notStartedCnt, nearestDueDate));
                }
            }
            result.sort(Comparator.comparing(MyProjectResult::getId));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getMyProjects", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addProject(Project project) {
        project.setStatus(1); //1: Not Started
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
    public List<ProjectMemberResult> getProjectMembers(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<ProjectMemberResult> result = _projectRepository.getProjectMembers(ids);
            result.sort(Comparator.comparing(ProjectMemberResult::getJoinDate));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getProjectMembers", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addMemberToProject(int memberId, int projectId, int role) throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.addMemberToProject(memberId, projectId, role);
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
    public List<ProjectStatusResult> getStatusResults() throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.getStatusResults();
        }
        catch (Exception e) {
            _logger.Error("getStatusResults", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProjectChartData> getChartData() throws ExecutionException, InterruptedException {
        try {
            return _projectRepository.getChartData();
        }
        catch (Exception e) {
            _logger.Error("getChartData", e.getMessage());
            throw e;
        }
    }
}
