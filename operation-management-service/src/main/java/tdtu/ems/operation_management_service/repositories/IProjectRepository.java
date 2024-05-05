package tdtu.ems.operation_management_service.repositories;

import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.operation_management_service.models.Project;
import tdtu.ems.operation_management_service.models.ProjectResult;
import tdtu.ems.operation_management_service.models.ProjectUpdate;
import tdtu.ems.operation_management_service.models.ProjectUpdateResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IProjectRepository {
    List<Project> getProjects();
    List<ProjectResult> getProjectResults();
    Project getProjectById(int id);
    ProjectResult getProjectResultById(int id);
    String addProject(Project project);
    String removeProject(int id) throws ExecutionException, InterruptedException;
    String editProject(Project project) throws ExecutionException, InterruptedException;
    String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException;
    List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException;
    ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException;
    int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException;
    String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException;
}
