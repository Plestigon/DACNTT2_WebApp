package tdtu.ems.main.models.finance;

public class DealDto {
    private int id;
    private String title;
    private int associate;
    private int contact;
    private double dealValue;

    public DealDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAssociate() {
        return associate;
    }

    public void setAssociate(int associate) {
        this.associate = associate;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public double getDealValue() {
        return dealValue;
    }

    public void setDealValue(double dealValue) {
        this.dealValue = dealValue;
    }
}
