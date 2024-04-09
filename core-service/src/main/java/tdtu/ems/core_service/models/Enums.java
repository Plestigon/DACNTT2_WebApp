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
        Demo,
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
}
