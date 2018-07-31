package co.jestrada.cupoescolarapp.login.interactor;


import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.common.constant.CustomDateUtils;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;

public class UserInteractor implements
        IBaseContract.IBaseInteractor{

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefUsers;

    private ILoginContract.ILoginPresenter mLoginPresenter;
    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;

    private UserBO userBOApp;

    public UserInteractor(ILoginContract.ILoginPresenter mLoginPresenter) {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.mLoginPresenter = mLoginPresenter;
        this.dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
    }

    public UserInteractor(ISignUpContract.ISignUpPresenter mSignUpPresenter) {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.mSignUpPresenter = mSignUpPresenter;
        this.dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
    }

    public UserInteractor(IMainContract.IMainPresenter mMainPresenter) {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.mMainPresenter = mMainPresenter;
        this.dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
    }

    public void getUser(final String userUid) {
        dbRefUsers.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userDS) {
                final UserDocJson userDocJson = userDS.getValue(UserDocJson.class);

                dbRefUsers.child(userUid).child(Firebase.USERS_LOGINS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot loginMethodDS) {
                        ArrayList<LoginMethodDocJson> loginMethodDocJsons = new ArrayList<>();
                        for( DataSnapshot loginMethodChildDS : loginMethodDS.getChildren()){
                            LoginMethodDocJson loginMethodDocJson = loginMethodChildDS.getValue(LoginMethodDocJson.class);
                            loginMethodDocJsons.add(loginMethodDocJson);
                        }
                        userBOApp = UserBO.getInstance();
                        userBOApp.setValues(userDocJson, loginMethodDocJsons);
                        notifyUserChanges();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: Handle error on presenter here.
            }
        });
    }

    private void notifyUserChanges() {
        if(mMainPresenter != null){
            mMainPresenter.getUser(userBOApp);
        }
    }

    public void saveUser(UserBO userBO) {
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
        UserDocJson userDocJson = new UserDocJson();
        ArrayList<LoginMethodBO> loginMethodBOS = new ArrayList<>();
        userDocJson.setValues(userBO);
        loginMethodBOS.addAll(userBO.getLogins());

        dbRefUsers.child(userDocJson.getuId())
                .setValue(userDocJson);

        for (LoginMethodBO loginMethod : loginMethodBOS){
            LoginMethodDocJson loginMethodDocJson = new LoginMethodDocJson();
            loginMethodDocJson.setValues(loginMethod);
            dbRefUsers.child(userDocJson.getuId())
                    .child(Firebase.USERS_LOGINS)
                    .child(loginMethodDocJson.getLoginMethod().toString())
                    .setValue(loginMethodDocJson);
        }

    }

    public void activateUser() {
        userBOApp.setState(StateUserEnum.ACTIVE);
        for(LoginMethodBO loginMethod : userBOApp.getLogins()){
            if (loginMethod.getLoginMethod().equals(LoginMethodEnum.EMAIL_AND_PASSWORD)){
                String strFecha = DateFormat.format(CustomDateUtils.LONG_DATE, new Date()).toString();
                loginMethod.setActivateTimestamp(strFecha);
                loginMethod.setState(StateUserEnum.EMAIL_VERIFIED);
            }
        }
        saveUser(userBOApp);
    }

}

