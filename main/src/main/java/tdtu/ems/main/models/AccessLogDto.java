package tdtu.ems.main.models;

import java.util.Date;

public class AccessLogDto {
    private String email;
    private Date loginTime;

    public AccessLogDto() {
    }

    public AccessLogDto(String email, Date loginTime) {
        this.email = email;
        this.loginTime = loginTime;
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
