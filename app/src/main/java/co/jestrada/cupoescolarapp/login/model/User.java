package co.jestrada.cupoescolarapp.login.model;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.userChilds.LoginMethodModel;

public class User {

    private static volatile User userModelInstance = new User();

    private String uId;
    private String email;
    private String firstName;
    private String lastName;
    private StateUserEnum state;
    private boolean onSession;
    private ArrayList<LoginMethodModel> logins;

    private User() {
        if (userModelInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static User getInstance(){
        if (userModelInstance == null){
            synchronized (User.class) {
                if (userModelInstance == null){
                    userModelInstance = new User();
                }
            }
        }
        return userModelInstance;
    }

    public void setUser(UserModel userModel){
        this.uId = userModel.getuId();
        this.email = userModel.getEmail();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.state = userModel.getState();
        this.logins = userModel.getLogins();
    }

    public static User getUserModelInstance() {
        return userModelInstance;
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


    public boolean isOnSession() {
        return onSession;
    }

    public void setOnSession(boolean onSession) {
        this.onSession = onSession;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }

    public ArrayList<LoginMethodModel> getLogins() {
        return logins;
    }

    public void setLogins(ArrayList<LoginMethodModel> logins) {
        this.logins = logins;
    }
}

