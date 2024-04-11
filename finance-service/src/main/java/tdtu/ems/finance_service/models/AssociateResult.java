package tdtu.ems.finance_service.models;

import java.util.List;

public class AssociateResult extends Associate {
    protected List<Contact> contactsObj;
    protected List<Deal> dealsObj;

    public AssociateResult() {
        super();
    }

    public AssociateResult(int id, String name, String domain, List<Integer> contacts, List<Integer> deals, int priority, String description, List<Contact> contactsObj, List<Deal> dealsObj) {
        super(id, name, domain, contacts, deals, priority, description);
        this.contactsObj = contactsObj;
        this.dealsObj = dealsObj;
    }

    public AssociateResult(Associate a, List<Contact> contactsObj, List<Deal> dealsObj) {
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

    public List<Deal> getDealsObj() {
        return dealsObj;
    }

    public void setDealsObj(List<Deal> dealsObj) {
        this.dealsObj = dealsObj;
    }
}
