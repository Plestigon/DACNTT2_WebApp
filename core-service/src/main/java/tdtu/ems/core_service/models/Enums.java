package tdtu.ems.core_service.models;

public class Enums {
    public enum Priority {
        Null,
        High,
        Medium,
        Low
    }

    public enum DealStage {
        Null,
        Discovery,
        Proposal,
        Negotiate,
        Lost,
        Won
    }

    public enum LeadStatus {
        Null,
        New,
        Rejected,
        Contacted
    }

    public enum ProjectStatus {
        NotStarted("Not Started"),
        InProgress("In Progress"),
        Finished("Finished"),
        Stopped("Stopped"),
        Cancelled("Cancelled");
        public final String name;
        ProjectStatus(String name) {this.name = name;}
    }

    public enum Role {
        None("None", 0),
        Intern("Intern", 1),
        Employee("Employee", 2),
        TeamLead("Team Lead", 3),
        HR("Human Resources", 4),
        HRH("Human Resources Head", 5),
        BranchManager("Branch Manager", 6),
        Owner("Owner", 7);

        public final String name;
        public final int ordinal;
        Role(String name, int ordinal) {
            this.name = name;
            this.ordinal = ordinal;
        }
    }
}
