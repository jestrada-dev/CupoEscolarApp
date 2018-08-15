package co.jestrada.cupoescolarapp.login.interactor;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.common.constant.CustomDateUtils;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.constants.ConstantsFirebaseUser;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;

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
        this.dbRefUsers = mFirebaseDB.getReference(ConstantsFirebaseUser.USERS);
    }

    @Override
    public void getUser(final String userUid) {
        dbRefUsers.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userDS) {
                final UserDocJson userDocJson = userDS.getValue(UserDocJson.class);
                Log.d("User","UserInteractor -> Se ejecutó el onDataChange para " + ConstantsFirebaseUser.USERS + "/" + userUid);

                dbRefUsers.child(userUid).child(ConstantsFirebaseUser.USER_LOGINS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot loginMethodDS) {
                        Log.d("User","UserInteractor -> Se ejecutó el onDataChange para " + ConstantsFirebaseUser.USERS + "/" + ConstantsFirebaseUser.USER_LOGINS);

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
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(ConstantsFirebaseUser.USERS);
        final UserDocJson userDocJson = new UserDocJson();
        final ArrayList<LoginMethodBO> loginMethodBOS = new ArrayList<>();
        userDocJson.setValues(userBO);
        loginMethodBOS.addAll(userBO.getLogins());

        dbRefUsers.child(userDocJson.getuId())
                .setValue(userDocJson).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("User","UserInteractor -> Usuario: email:" + userDocJson.getEmail() + " grabado exitosamente");
                    for (LoginMethodBO loginMethod : loginMethodBOS){
                        final LoginMethodDocJson loginMethodDocJson = new LoginMethodDocJson();

                        loginMethodDocJson.setValues(loginMethod);

                        dbRefUsers.child(userDocJson.getuId())
                                .child(ConstantsFirebaseUser.USER_LOGINS)
                                .child(loginMethodDocJson.getLoginMethod().toString())
                                .setValue(loginMethodDocJson).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("User","UserInteractor -> Usuario: email:" + userDocJson.getEmail() +
                                            " LoginMethod: " + loginMethodDocJson.getLoginMethod() + " grabado exitosamente");
                                }
                            }
                        });
                    }
                }
            }
        });



    }

    @Override
    public void activateUser() {
        userBOApp = UserBO.getInstance();

        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(ConstantsFirebaseUser.USERS);
        dbRefUsers.child(userBOApp.getuId())
                .child(ConstantsFirebaseUser.USER_FIELD_STATE)
                .setValue(StateUserEnum.ACTIVE).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("User","UserInteractor -> Usuario: email:" + userBOApp.getEmail() +
                            " activado exitosamente");

                }
            }
        });

        dbRefUsers.child(userBOApp.getuId())
                .child(ConstantsFirebaseUser.USER_LOGINS)
                .child(ConstantsFirebaseUser.USER_LOGIN_METHOD_EMAIL_PASSWORD)
                .child(ConstantsFirebaseUser.USER_FIELD_STATE)
                .setValue(StateUserEnum.EMAIL_VERIFIED).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("User","UserInteractor -> Usuario: email:" + userBOApp.getEmail() +
                            " LoginMethod: " + ConstantsFirebaseUser.USER_LOGIN_METHOD_EMAIL_PASSWORD + " grabado exitosamente");

                }
            }
        });

        final String strFecha = DateFormat.format(CustomDateUtils.LONG_DATE, new Date()).toString();
        dbRefUsers.child(userBOApp.getuId())
                .child(ConstantsFirebaseUser.USER_LOGINS)
                .child(ConstantsFirebaseUser.USER_LOGIN_METHOD_EMAIL_PASSWORD)
                .child(ConstantsFirebaseUser.USER_FIELD_ACTIVATE_TIMESTAMP)
                .setValue(strFecha).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("User","UserInteractor -> Usuario: email:" + userBOApp.getEmail() +
                            " LoginMethod: " + ConstantsFirebaseUser.USER_LOGIN_METHOD_EMAIL_PASSWORD + " fecha:" +
                            strFecha + " grabado exitosamente");
                }
            }
        });
    }

}

