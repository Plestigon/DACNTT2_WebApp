package tdtu.ems.finance_service.models;

import tdtu.ems.core_service.models.Enums;

import java.util.List;

public class AssociateResult extends Associate {
    protected List<Contact> contactsObj;
    protected List<DealResult> dealsObj;

    public AssociateResult() {
        super();
    }

    public AssociateResult(int id, String name, String domain, List<Integer> contacts, List<Integer> deals, Enums.Priority priority, String description, List<Contact> contactsObj, List<DealResult> dealsObj) {
        super(id, name, domain, contacts, deals, priority, description);
        this.contactsObj = contactsObj;
        this.dealsObj = dealsObj;
    }

    public AssociateResult(Associate a, List<Contact> contactsObj, List<DealResult> dealsObj) {
        super(a.id, a.name, a.domain, a.contacts, a.deals, a.priority, a.description);
        this.contactsObj = contactsObj;
        this.dealsObj = dealsObj;
    }

    public List<Contact> getContactsObj() {
        return contactsObj;
    }

    public void setContactsObj(List<Contact> contactsObj) {
        this.contactsObj = contactsObj;
    }

    public List<DealResult> getDealsObj() {
        return dealsObj;
    }

    public void setDealsObj(List<DealResult> dealsObj) {
        this.dealsObj = dealsObj;
    }
}
