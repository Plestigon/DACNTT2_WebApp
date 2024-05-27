package tdtu.ems.operation_management_service.models;

import java.util.Date;

public class MyProjectResult extends ProjectResult {
    private ProjectMemberResult memberInfo;
    private int tasksInProgress;
    private int tasksNotStarted;
    private Date nearestDueDate;

    public MyProjectResult(Project p, String ownerName, ProjectMemberResult memberInfo, int tasksInProgress, int tasksNotStarted, Date nearestDueDate) {
        super(p, ownerName);
        this.memberInfo = memberInfo;
        this.tasksInProgress = tasksInProgress;
        this.tasksNotStarted = tasksNotStarted;
        this.nearestDueDate = nearestDueDate;
    }

    public MyProjectResult(ProjectResult p, ProjectMemberResult memberInfo, int tasksInProgress, int tasksNotStarted, Date nearestDueDate) {
        super(p.getId(), p.getName(), p.getOwnerId(), p.getOwnerName(), p.getMemberIds(), p.getStatus(), p.getStatusName(), p.getCreateDate(), p.getDueDate(), p.getDescription(), p.getProjectUpdateIds());
        this.memberInfo = memberInfo;
        this.tasksInProgress = tasksInProgress;
        this.tasksNotStarted = tasksNotStarted;
        this.nearestDueDate = nearestDueDate;
    }

    public ProjectMemberResult getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(ProjectMemberResult memberInfo) {
        this.memberInfo = memberInfo;
    }

    public int getTasksInProgress() {
        return tasksInProgress;
    }

    public void setTasksInProgress(int tasksInProgress) {
        this.tasksInProgress = tasksInProgress;
    }

    public int getTasksNotStarted() {
        return tasksNotStarted;
    }

    public void setTasksNotStarted(int tasksNotStarted) {
        this.tasksNotStarted = tasksNotStarted;
    }

    public Date getNearestDueDate() {
        return nearestDueDate;
    }

    public void setNearestDueDate(Date nearestDueDate) {
        this.nearestDueDate = nearestDueDate;
    }
}
