package tdtu.ems.operation_management_service.services;

import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;

import java.util.List;

public interface IProjectService {
    List<ProjectResult> getProjects();
    ProjectResult getProjectById(int id);
    String addProject(Project project);
    String removeProject(int id);
    String editProject(Project project);
    String updateProjectStatus(int id, int status);
    List<ProjectUpdate> getProjectUpdates(int projectId);
    ProjectUpdate getProjectUpdateById(int id);
    String addProjectUpdate(ProjectUpdate projectUpdate, int projectId);
    String addProjectUpdateToProject(int projectUpdateId, int projectId);
    Object test();
}
