package tdtu.ems.finance_service.models;

public class Lead {
    private int id;
    private String name;
    private int status;
    private String company;
    private String title;
    private String email;
    private String phoneNumber;

    public Lead() {}

    public Lead(int id, String name, int status, String company, String title, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.company = company;
        this.title = title;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
