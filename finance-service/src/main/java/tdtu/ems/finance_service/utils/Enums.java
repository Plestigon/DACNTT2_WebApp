package tdtu.ems.finance_service.utils;

public class Enums {
    public enum Priority {
        None,
        Low,
        Medium,
        High
    }

    public enum Role {
        None("None"),
        Intern("Intern"),
        Employee("Employee"),
        TeamLead("Team Lead"),
        HR("Human Resources"),
        HRH("Human Resources Head"),
        BranchManager("Branch Manager"),
        Owner("Owner");

        public final String name;
        Role(String name) {
            this.name = name;
        }
    }

    //region Operation
    public enum ProjectStatus {
        None("None"),
        NotStarted("Not Started"),
        InProgress("In Progress"),
        Finished("Finished"),
        Paused("Paused"),
        Cancelled("Cancelled");
        public final String name;
        ProjectStatus(String name) {this.name = name;}
    }

    public enum ProjectRole {
        None,
        Lead,
        Dev,
        BA,
        QC
    }

    public enum TaskState {
        None("None"),
        ToDo("To Do"),
        InProgress("In Progress"),
        Done("Done"),
        Removed("Removed");
        public final String name;
        TaskState(String name) {
            this.name = name;
        }
    }
    //endregion

    //region HR
    public enum ContractStatus {
        None,
        Inactive,
        Active,
        Ended
    }

    public enum FormStatus {
        None("None"),
        WaitingForApproval("Waiting For Approval"),
        Approved("Approved"),
        Rejected("Rejected");

        public final String name;
        FormStatus(String name) {
            this.name = name;
        }
    }

    public enum ContractType {
        None("None"),
        UnpaidInternship("Unpaid Internship"),
        PaidInternship("Paid Internship"),
        Probationary("Probationary"),
        PartTimeEmployment("Part-time Employment"),
        FullTimeEmployment("Full-time Employment");

        public final String name;
        ContractType(String name) {
            this.name = name;
        }
    }

    public enum FormType {
        None("None"),
        AnnualLeave("Annual Leave"),
        UnpaidLeave("Unpaid Leave"),
        Resignation("Resignation");

        public final String name;
        FormType(String name) {
            this.name = name;
        }
    }
    //endregion

    //region Finance
    public enum DealStage {
        None,
        Discovery,
        Proposal,
        Negotiate,
        Ended
    }

    public enum LeadStatus {
        None,
        New,
        Rejected,
        Contacted
    }
    //endregion
}
