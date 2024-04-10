package tdtu.ems.finance_service.models;

import java.util.List;
import tdtu.ems.core_service.models.Enums;

public class Associate {
    protected int id;
    protected String name;
    protected String domain;
    protected List<Integer> contacts;
    protected List<Integer> deals;
    protected Enums.Priority priority;
    protected String description;

    public Associate() {}

    public Associate(int id, String name, String domain, List<Integer> contacts, List<Integer> deals, Enums.Priority priority, String description) {
        this.id = id;
        this.name = name;
        this.domain = domain;
        this.contacts = contacts;
        this.deals = deals;
        this.priority = priority;
        this.description = description;
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

    public List<Integer> getContacts() {
        return contacts;
    }

    public void setContacts(List<Integer> contacts) {
        this.contacts = contacts;
    }

    public List<Integer> getDeals() {
        return deals;
    }

    public void setDeals(List<Integer> deals) {
        this.deals = deals;
    }

    public Enums.Priority getPriority() {
        return priority;
    }

    public void setPriority(Enums.Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}