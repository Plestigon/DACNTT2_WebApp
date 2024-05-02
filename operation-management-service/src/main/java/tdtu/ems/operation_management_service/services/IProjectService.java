package tdtu.ems.operation_management_service.services;

import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.models.ProjectUpdateResult;

import java.util.List;

public interface IProjectService {
    List<ProjectResult> getProjects();
    ProjectResult getProjectById(int id);
    String addProject(Project project);
    String removeProject(int id);
    BaseResponse editProject(Project project);
    String updateProjectStatus(int id, int status);
    List<ProjectUpdateResult> getProjectUpdates(int projectId);
    ProjectUpdate getProjectUpdateById(int id);
    String addProjectUpdate(ProjectUpdate projectUpdate, int projectId);
    String addProjectUpdateToProject(int projectUpdateId, int projectId);
    Object test();
}
