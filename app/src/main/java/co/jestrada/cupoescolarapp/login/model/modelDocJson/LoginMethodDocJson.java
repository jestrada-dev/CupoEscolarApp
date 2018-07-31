package co.jestrada.cupoescolarapp.login.model.modelDocJson;

import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;

public class LoginMethodDocJson {

    private LoginMethodEnum loginMethod;
    private String email;
    private String creationTimestamp;
    private String activateTimestamp;
    private StateUserEnum state;

    public LoginMethodDocJson() {
    }

    public void setValues(LoginMethodBO loginMethodBO){
        this.loginMethod = loginMethodBO.getLoginMethod();
        this.email = loginMethodBO.getEmail();
        this.creationTimestamp = loginMethodBO.getCreationTimestamp();
        this.state = loginMethodBO.getState();
        this.activateTimestamp = loginMethodBO.getActivateTimestamp();
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

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }

    public String getActivateTimestamp() {
        return activateTimestamp;
    }

    public void setActivateTimestamp(String activateTimestamp) {
        this.activateTimestamp = activateTimestamp;
    }
}
