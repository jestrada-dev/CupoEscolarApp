package co.jestrada.cupoescolarapp.login.model.modelDocJson;

import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class UserDocJson {

    private String uId;
    private String email;
    private StateUserEnum state;

    public UserDocJson() {
    }

    public void setValues(UserBO userBO){
        this.uId = userBO.getuId();
        this.email = userBO.getEmail();
        this.state = userBO.getState();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }
}
