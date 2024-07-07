package tdtu.ems.employee_service.models;

import java.util.Date;

public class AccessLog {
    private int id;
    private String email;
    private Date loginTime;

    public AccessLog() {
    }

    public AccessLog(int id, String email, Date loginTime) {
        this.id = id;
        this.email = email;
        this.loginTime = loginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
