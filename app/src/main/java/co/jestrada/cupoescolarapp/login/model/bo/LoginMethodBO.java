package co.jestrada.cupoescolarapp.login.model.bo;

import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;

public class LoginMethodBO {

    private LoginMethodEnum loginMethod;
    private String email;
    private String creationTimestamp;
    private String activateTimestamp;
    private StateUserEnum state;

    public LoginMethodBO() {
    }

    public void setValues(LoginMethodDocJson loginMethodDocJson){
        this.loginMethod = loginMethodDocJson.getLoginMethod();
        this.email = loginMethodDocJson.getEmail();
        this.creationTimestamp = loginMethodDocJson.getCreationTimestamp();
        this.state = loginMethodDocJson.getState();
        this.activateTimestamp = loginMethodDocJson.getActivateTimestamp();
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
