package tdtu.ems.operation_management_service.services;

import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.operation_management_service.models.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IProjectService {
    List<ProjectResult> getProjects();
    ProjectResult getProjectById(int id);
    String addProject(Project project);
    String removeProject(int id) throws ExecutionException, InterruptedException;
    String editProject(Project project) throws ExecutionException, InterruptedException;
    String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException;
    List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException;
    ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException;
    int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException;
    String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException;
    List<ProjectMemberResult> getProjectMembers(List<Integer> ids) throws ExecutionException, InterruptedException;
    String addMemberToProject(int memberId, int projectId, int role) throws ExecutionException, InterruptedException;
    String removeMemberFromProject(int memberId, int projectId) throws ExecutionException, InterruptedException;
    Object test();
}
