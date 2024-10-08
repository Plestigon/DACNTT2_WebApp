package tdtu.ems.operation_management_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Repository;
import tdtu.ems.operation_management_service.utils.ChartData;
import tdtu.ems.operation_management_service.utils.Logger;
import tdtu.ems.operation_management_service.models.*;
import tdtu.ems.operation_management_service.utils.Enums;
import tdtu.ems.operation_management_service.utils.PagedResponse;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ProjectRepository implements IProjectRepository {
    private final Firestore _db;
    private final Logger<ProjectRepository> _logger;

    public ProjectRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ProjectRepository.class);
    }


    @Override
    public PagedResponse getProjects(int page, String search, Integer status) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        CollectionReference employeesDb = _db.collection("employees");
        List<ProjectResult> projects = new ArrayList<>();
        for (DocumentSnapshot data : projectsDb.get().get().getDocuments()) {
            Project prj = data.toObject(Project.class);
            if (prj != null) {
                String a = search.toLowerCase();
                String b = prj.getName().toLowerCase();
                if (!search.isEmpty() && !prj.getName().toLowerCase().contains(search.toLowerCase())) {
                    continue;
                }
                if (status != null && prj.getStatus() != status) {
                    continue;
                }
                DocumentSnapshot ownerData = employeesDb.document(String.valueOf(prj.getOwnerId())).get().get();
                ProjectResult prjRes = new ProjectResult(prj, ownerData.exists() ? ownerData.getString("name") : "N/A");
                projects.add(prjRes);
            }
        }
        int totalCount = projects.size();
        projects.sort(Comparator.comparing(ProjectResult::getCreateDate).reversed());
        //Paging
        int startIndex = (page-1)*10;
        if (startIndex >= projects.size()) {
            return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
        }
        var result = projects.subList(startIndex, Math.min(startIndex + 10, projects.size()));
        return new PagedResponse(result, 200, "OK", totalCount, page, 10);
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
    public ProjectResult getProjectResultById(int id) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            CollectionReference employeesDb = _db.collection("employees");
            DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
            if (data.exists()) {
                Project project = data.toObject(Project.class);
                if (project != null) {
                    DocumentSnapshot ownerData = employeesDb.document(String.valueOf(project.getOwnerId())).get().get();
                    if (ownerData.exists()) {
                        ProjectResult result = new ProjectResult(project, ownerData.getString("name"));
                        return result;
                    }
                }
                _logger.Error("getProjectResultById", "Project is null.");
                return null;
            }
            _logger.Error("getProjectResultById", "Data does not exist.");
            return null;
        }
        catch (Exception e) {
            _logger.Error("getProjectResultById", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProjectResult> getProjectsByEmployeeId(int id) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        CollectionReference employeesDb = _db.collection("employees");
        CollectionReference projectMembersDb = _db.collection("projectMembers");
        List<ProjectResult> projects = new ArrayList<>();
        for (DocumentSnapshot data : projectMembersDb.get().get().getDocuments()) {
            ProjectMember pm = data.toObject(ProjectMember.class);
            if (pm != null && pm.getEmployeeId() == id) {
                Project prj = projectsDb.document(String.valueOf(pm.getProjectId())).get().get().toObject(Project.class);
                if (prj != null) {
                    DocumentSnapshot ownerData = employeesDb.document(String.valueOf(prj.getOwnerId())).get().get();
                    projects.add(new ProjectResult(prj, ownerData.getString("name")));
                }
            }
        }
        return projects;
    }

    @Override
    public String addProject(Project project) {
        try {
            CollectionReference projectsDb = _db.collection("projects");
            DocumentReference idTracer = _db.collection("idTracer").document("projects");
            long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            project.setId((int) id);
            project.setCreateDate(new Date());
            ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).set(project);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String removeProject(int id) throws ExecutionException, InterruptedException {
        Project project = getProjectById(id);
        if (project == null) {
            throw new NotFoundException();
        }
        ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String editProject(Project entry) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        Project existing = projectsDb.document(String.valueOf(entry.getId())).get().get().toObject(Project.class);
        if (existing == null) {
            throw new NotFoundException();
        }
        existing.setName(entry.getName());
        existing.setDueDate(entry.getDueDate());
        existing.setDescription(entry.getDescription());
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(entry.getId())).set(existing);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String updateProjectStatus(int id, int status) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(id)).update("status", status);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public List<ProjectUpdateResult> getProjectUpdates(int projectId) throws ExecutionException, InterruptedException {
        CollectionReference employeesDb = _db.collection("employees");
        Project prj = getProjectById(projectId);
        if (prj == null) {
            _logger.Error("getProjectUpdates", "Project not found. id: " + projectId);
            throw new NotFoundException();
        }
        List<Integer> projectUpdateIds = prj.getProjectUpdateIds();
        List<ProjectUpdateResult> res = new ArrayList<>();
        if (projectUpdateIds != null) {
            for (int i : projectUpdateIds) {
                ProjectUpdate pu = getProjectUpdateById(i);
                if (pu == null) continue;
                String writerName = "<SYSTEM>";
                if (pu.getWriterId() > 0) {
                    writerName = employeesDb.document(String.valueOf(pu.getWriterId())).get().get().getString("name");
                }
                res.add(new ProjectUpdateResult(pu, writerName));
            }
        }
        return res;
    }

    @Override
    public ProjectUpdate getProjectUpdateById(int id) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projectUpdates");
        DocumentSnapshot data = projectsDb.document(String.valueOf(id)).get().get();
        ProjectUpdate projectUpdate = null;
        if (data.exists()) {
            projectUpdate = data.toObject(ProjectUpdate.class);
        }
        return projectUpdate;
    }

    @Override
    public int addProjectUpdate(ProjectUpdate projectUpdate, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectUpdatesDb = _db.collection("projectUpdates");
        DocumentReference idTracer = _db.collection("idTracer").document("projectUpdates");
        long projectUpdateId = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        projectUpdate.setId((int) projectUpdateId);
        projectUpdate.setCreateTime(new Date());
        ApiFuture<WriteResult> result = projectUpdatesDb.document(String.valueOf(projectUpdateId)).set(projectUpdate);

        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", projectUpdateId);

        return (int)projectUpdateId;
    }

    @Override
    public String addProjectUpdateToProject(int projectUpdateId, int projectId) throws ExecutionException, InterruptedException {
        List<Integer> projectUpdateIds = getProjectById(projectId).getProjectUpdateIds();
        if (projectUpdateIds == null) {
            projectUpdateIds = new ArrayList<>();
        }
        projectUpdateIds.add(projectUpdateId);
        ApiFuture<WriteResult> result = _db.collection("projects").document(String.valueOf(projectId))
                .update("projectUpdateIds", projectUpdateIds);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public List<ProjectMemberResult> getProjectMembers(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference projectMembersDb = _db.collection("projectMembers");
        CollectionReference employeesDb = _db.collection("employees");
        List<ProjectMemberResult> result = new ArrayList<>();
        for (int i : ids) {
            ProjectMember pm = projectMembersDb.document(String.valueOf(i)).get().get().toObject(ProjectMember.class);
            if (pm != null) {
                String name = employeesDb.document(String.valueOf(pm.getEmployeeId())).get().get().getString("name");
                String email = employeesDb.document(String.valueOf(pm.getEmployeeId())).get().get().getString("email");
                result.add(new ProjectMemberResult(pm, name, email));
            }
        }
        return result;
    }

    @Override
    public String addMemberToProject(int employeeId, int projectId, int role) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        CollectionReference projectMembersDb = _db.collection("projectMembers");
        DocumentReference idTracer = _db.collection("idTracer").document("projectMembers");
        long memberId = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        Project prj = projectsDb.document(String.valueOf(projectId)).get().get().toObject(Project.class);
        if (prj == null) {
            throw new NotFoundException();
        }

        ProjectMember pm = new ProjectMember((int)memberId, projectId, employeeId, new Date(), role);
        ApiFuture<WriteResult> memberResult = projectMembersDb.document(String.valueOf(memberId)).set(pm);
        if (memberResult.get() != null) {
            prj.getMemberIds().add((int)memberId);
            ApiFuture<WriteResult> result = projectsDb.document(String.valueOf(projectId)).set(prj);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", memberId);
            return result.get().getUpdateTime().toString();
        }
        else {
            throw new InternalError("Add ProjectMember to database failed");
        }
    }

    @Override
    public String removeMemberFromProject(int memberId, int projectId) throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        CollectionReference projectMembersDb = _db.collection("projectMembers");
        Project prj = projectsDb.document(String.valueOf(projectId)).get().get().toObject(Project.class);
        if (prj == null || !prj.getMemberIds().contains(memberId) || projectMembersDb.document(String.valueOf(memberId)).get().get() == null) {
            throw new NotFoundException();
        }
        ApiFuture<WriteResult> result = projectMembersDb.document(String.valueOf(memberId)).delete();
        prj.getMemberIds().remove(Integer.valueOf(memberId));
        ApiFuture<WriteResult> result2 = projectsDb.document(String.valueOf(projectId)).set(prj);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public List<ProjectStatusResult> getStatusResults() throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        List<ProjectStatusResult> result = new ArrayList<>();
        result.add(new ProjectStatusResult(0, "All", 0));
        for (var status : Enums.ProjectStatus.values()) {
            if (status == Enums.ProjectStatus.None) continue;
            result.add(new ProjectStatusResult(status.ordinal(), status.name, 0));
        }

        for (var data : projectsDb.get().get().getDocuments()) {
            Project prj = data.toObject(Project.class);
            result.get(0).count++;
            result.get(prj.getStatus()).count++;
        }
        return result;
    }

    @Override
    public List<ChartData> getChartData() throws ExecutionException, InterruptedException {
        CollectionReference projectsDb = _db.collection("projects");
        List<ChartData> result = new ArrayList<>();
        for (var status : Enums.ProjectStatus.values()) {
            if (status == Enums.ProjectStatus.None) continue;
            String color = "";
            switch (status) {
                case NotStarted -> color = "#808080";
                case InProgress -> color = "#00FF00";
                case Finished -> color = "#0000FF";
                case Paused -> color = "#FFA500";
                case Cancelled -> color = "#FF0000";
            }
            result.add(new ChartData(status.ordinal(), status.name, 0, color));
        }

        for (var data : projectsDb.get().get().getDocuments()) {
            Project prj = data.toObject(Project.class);
            result.get(prj.getStatus()-1).value++;
        }
        return result;
    }
}
