package co.jestrada.cupoescolarapp.login.interactor;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.attendant.view.MainActivity;
import co.jestrada.cupoescolarapp.common.AppCore;
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
import co.jestrada.cupoescolarapp.login.view.LoginActivity;
import co.jestrada.cupoescolarapp.login.view.SignUpActivity;

public class UserInteractor implements
        IBaseContract.IBaseInteractor,
        ILoginContract.IUserInteractor,
        ISignUpContract.IUserInteractor{

    private ILoginContract.ILoginPresenter mLoginPresenter;
    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefUsers;

    private UserBO userBOApp;

    public UserInteractor(@Nullable ILoginContract.ILoginPresenter mLoginPresenter,
                          @Nullable ISignUpContract.ISignUpPresenter mSignUpPresenter,
                          @Nullable IMainContract.IMainPresenter mMainPresenter) {
        this.mLoginPresenter = mLoginPresenter;
        this.mSignUpPresenter = mSignUpPresenter;
        this.mMainPresenter = mMainPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
    }

    @Override
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
                        //notifyUserChanges();
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
        if(mLoginPresenter != null){
        }
        if(mSignUpPresenter != null){
        }
        if(mMainPresenter != null){
            mMainPresenter.getUser(userBOApp);
        }
    }

    @Override
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

    @Override
    public void activateUser() {
        userBOApp = UserBO.getInstance();
        //userBOApp.setState(StateUserEnum.ACTIVE);
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
        dbRefUsers.child(userBOApp.getuId())
                .child(Firebase.FIELD_STATE)
                .setValue(StateUserEnum.ACTIVE);

        dbRefUsers.child(userBOApp.getuId())
                .child(Firebase.USERS_LOGINS)
                .child(Firebase.LOGIN_METHOD_EMAIL_PASSWORD)
                .child(Firebase.FIELD_STATE)
                .setValue(StateUserEnum.EMAIL_VERIFIED);

        String strFecha = DateFormat.format(CustomDateUtils.LONG_DATE, new Date()).toString();
        dbRefUsers.child(userBOApp.getuId())
                .child(Firebase.USERS_LOGINS)
                .child(Firebase.LOGIN_METHOD_EMAIL_PASSWORD)
                .child(Firebase.FIELD_ACTIVATE_TIMESTAMP)
                .setValue(strFecha);
    }

}

