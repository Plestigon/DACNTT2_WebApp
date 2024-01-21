package tdtu_ems.userservice.models;

import java.util.List;

public class Department {
    private int id;
    private String shortName;
    private String longName;
    private List<Integer> userIds;

    public Department(){}

    public Department(int id, String shortName, String longName, List<Integer> userIds) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.userIds = userIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}