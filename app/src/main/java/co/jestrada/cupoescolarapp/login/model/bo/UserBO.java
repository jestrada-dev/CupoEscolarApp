package co.jestrada.cupoescolarapp.login.model.bo;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;

public class UserBO {

    private static volatile UserBO userBOModelInstance = new UserBO();

    private String uId;
    private String email;
    private String firstName;
    private String lastName;
    private StateUserEnum state;
    private boolean onSession;
    private ArrayList<LoginMethodBO> logins;

    private UserBO() {
        if (userBOModelInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static UserBO getInstance(){
        if (userBOModelInstance == null){
            synchronized (UserBO.class) {
                if (userBOModelInstance == null){
                    userBOModelInstance = new UserBO();
                }
            }
        }
        return userBOModelInstance;
    }

    public void setValues(UserDocJson userDocJson, ArrayList<LoginMethodDocJson> loginMethodDocJsons){
        this.uId = userDocJson.getuId();
        this.email = userDocJson.getEmail();
        this.firstName = userDocJson.getFirstName();
        this.lastName = userDocJson.getLastName();
        this.state = userDocJson.getState();
        ArrayList<LoginMethodBO> loginMethodBOS = new ArrayList<>();
        LoginMethodBO loginMethodBO = new LoginMethodBO();
        for (LoginMethodDocJson loginMethod : loginMethodDocJsons){
            loginMethodBO.setValues(loginMethod);
            loginMethodBOS.add(loginMethodBO);
        }
        this.logins = loginMethodBOS;
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

}

