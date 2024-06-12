package tdtu.ems.finance_service.models;

import java.util.List;

public class AssociateResult {
    private int id;
    private String name;
    private String domain;
    private String description;
    private List<Contact> contacts;

    public AssociateResult() {}

    public AssociateResult(Associate o, List<Contact> contacts) {
        this.id = o.getId();
        this.name = o.getName();
        this.domain = o.getDomain();
        this.description = o.getDescription();
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
