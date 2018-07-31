package co.jestrada.cupoescolarapp.attendant.interactor;


import android.support.annotation.NonNull;
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

import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.common.constant.CustomDateUtils;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;

public class AttendantInteractor implements
        IBaseContract.IBaseInteractor{

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefAttendants;

    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public AttendantInteractor(ISignUpContract.ISignUpPresenter mSignUpPresenter) {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.mSignUpPresenter = mSignUpPresenter;
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public AttendantInteractor(IMainContract.IMainPresenter mMainPresenter) {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.mMainPresenter = mMainPresenter;
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                // TODO: Modelar la clase AttentantDocJson
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: Handle error on presenter here.
            }
        });
    }

    private void notifyAttandantChanges() {
        if(mMainPresenter != null){
            mMainPresenter.getUser(userBOApp);
        }
    }

    //TODO: Handle error on presenter here.
    public void saveAttendant(AttendantBO attendantBO) {
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
        AttendantDocJson attendantDocJson = new AttendantDocJson();
        attendantDocJson.setValues(attendantBO);

/*        ArrayList<LoginMethodBO> loginMethodBOS = new ArrayList<>();
        loginMethodBOS.addAll(userBO.getLogins());*/

        dbRefUsers.child(attendantDocJson.getuId())
                .setValue(attendantDocJson);

/*        for (LoginMethodBO loginMethod : loginMethodBOS){
            LoginMethodDocJson loginMethodDocJson = new LoginMethodDocJson();
            loginMethodDocJson.setValues(loginMethod);
            dbRefUsers.child(userDocJson.getuId())
                    .child(Firebase.USERS_LOGINS)
                    .child(loginMethodDocJson.getLoginMethod().toString())
                    .setValue(loginMethodDocJson);
        }*/

    }

}

