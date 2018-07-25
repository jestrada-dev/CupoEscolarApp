package co.jestrada.cupoescolarapp.login.model.userChilds;

import java.util.Date;

import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.LoginMethodEnum;

public class LoginMethodModel {

    private LoginMethodEnum loginMethod;
    private String email;
    private Date creationTimestamp;
    private StateUserEnum state;

    public LoginMethodModel() {
    }

    public LoginMethodEnum getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(LoginMethodEnum loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }

}
