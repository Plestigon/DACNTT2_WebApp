package tdtu.ems.main.models.finance;

public class AssociateDto {
    private String name;
    private String domain;
    private String description;

    public AssociateDto() {}

    public AssociateDto(String name, String domain, String description) {
        this.name = name;
        this.domain = domain;
        this.description = description;
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
}
