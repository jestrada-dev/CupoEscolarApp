package co.jestrada.cupoescolarapp.login.model.modelDocJson;

import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class UserDocJson {

    private String uId;
    private String email;
    private String firstName;
    private String lastName;
    private StateUserEnum state;

    public UserDocJson() {
    }

    public void setValues(UserBO userBO){
        this.uId = userBO.getuId();
        this.email = userBO.getEmail();
        this.firstName = userBO.getFirstName();
        this.lastName = userBO.getLastName();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }
}
