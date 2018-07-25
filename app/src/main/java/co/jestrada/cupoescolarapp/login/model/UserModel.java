package co.jestrada.cupoescolarapp.login.model;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.userChilds.LoginMethodModel;

public class UserModel {

    private String uId;
    private String email;
    private String firstName;
    private String lastName;
    private StateUserEnum state;
    private ArrayList<LoginMethodModel> logins;

    public UserModel() {
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

    public ArrayList<LoginMethodModel> getLogins() {
        return logins;
    }

    public void setLogins(ArrayList<LoginMethodModel> logins) {
        this.logins = logins;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }
}
