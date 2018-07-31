package co.jestrada.cupoescolarapp.login.interactor;


import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;

public class UserInteractor implements
        IBaseContract.IBaseInteractor{

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefUsers;

    private ILoginContract.ILoginPresenter mLoginPresenter;
    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;

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
            public void onDataChange(DataSnapshot userDataSnapshot) {
                final UserDocJson userDocJson = userDataSnapshot.getValue(UserDocJson.class);

                dbRefUsers.child(userUid).child(Firebase.USERS_LOGINS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot loginMethodDataSnapshot) {
                        ArrayList<LoginMethodDocJson> loginMethodDocJsons = new ArrayList<>();
                        for( DataSnapshot loginDataSnapshot : loginMethodDataSnapshot.getChildren()){
                            LoginMethodDocJson loginMethodDocJson = loginDataSnapshot.getValue(LoginMethodDocJson.class);
                            loginMethodDocJsons.add(loginMethodDocJson);
                        }
                        UserBO userBOApp = UserBO.getInstance();
                        userBOApp.setValues(userDocJson, loginMethodDocJsons);
                        sendUser(userBOApp);
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

    private void sendUser(UserBO userBOApp) {
        if(mLoginPresenter != null){
            mLoginPresenter.getUser(userBOApp);
        }
        if(mMainPresenter != null){
            mMainPresenter.getUser(userBOApp);
        }
    }

    public void saveUser(UserBO userBO, LoginMethodBO loginMethodBO) {
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
        dbRefUsers.child(userBO.getuId())
                .setValue(userBO);
        dbRefUsers.child(userBO.getuId())
                .child(Firebase.USERS_LOGINS)
                .child(loginMethodBO.getLoginMethod().toString())
                .setValue(loginMethodBO);
    }

}

