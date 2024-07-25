package tdtu.ems.operation_management_service.repositories;

import tdtu.ems.operation_management_service.models.*;
import tdtu.ems.operation_management_service.utils.ChartData;
import tdtu.ems.operation_management_service.utils.PagedResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IProjectRepository {
    PagedResponse getProjects(int page, String search, Integer status) throws ExecutionException, InterruptedException;
    Project getProjectById(int id);
    ProjectResult getProjectResultById(int id);
    List<ProjectResult> getProjectsByEmployeeId(int id) throws ExecutionException, InterruptedException;
    String addProject(Project project);
    String removeProject(int id) throws ExecutionException, InterruptedException;
    String editProject(Project project) throws ExecutionException, InterruptedException;
    String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException;
    List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException;
    ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException;
    int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException;
    String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException;
    List<ProjectMemberResult> getProjectMembers(List<Integer> ids) throws ExecutionException, InterruptedException;
    String addMemberToProject(int employeeId, int projectId, int role) throws ExecutionException, InterruptedException;
    String removeMemberFromProject(int employeeId, int projectId) throws ExecutionException, InterruptedException;
    List<ProjectStatusResult> getStatusResults() throws ExecutionException, InterruptedException;
    List<ChartData> getChartData() throws ExecutionException, InterruptedException;
}
