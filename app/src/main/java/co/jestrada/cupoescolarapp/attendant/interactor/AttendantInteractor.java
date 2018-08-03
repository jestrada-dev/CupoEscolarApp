package co.jestrada.cupoescolarapp.attendant.interactor;


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

import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPointDocJson;
import co.jestrada.cupoescolarapp.common.constant.CustomDateUtils;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.LoginMethodDocJson;
import co.jestrada.cupoescolarapp.login.model.modelDocJson.UserDocJson;

public class AttendantInteractor implements
        IBaseContract.IBaseInteractor,
        ISignUpContract.IAttendantInteractor{

    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;
    private ILoginContract.ILoginPresenter mLoginPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefAttendants;

    public AttendantInteractor(@Nullable ISignUpContract.ISignUpPresenter mSignUpPresenter,
                               @Nullable IMainContract.IMainPresenter mMainPresenter,
                               @Nullable ILoginContract.ILoginPresenter mLoginPresenter) {
        this.mSignUpPresenter = mSignUpPresenter;
        this.mMainPresenter = mMainPresenter;
        this.mLoginPresenter = mLoginPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
    }

    @Override
    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);

                dbRefAttendants.child(userUid)
                        .child(Firebase.ATTENDANTS_REF_POINT).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot refPointDS) {
                        final RefPointDocJson refPointDocJson = refPointDS.getValue(RefPointDocJson.class);
                        AttendantBO attendantBO = new AttendantBO();
                        attendantBO.setValues(attendantDocJson, refPointDocJson);
                        notifyAttandantChanges(attendantBO);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyAttandantChanges(AttendantBO attendantBO) {
        if(mMainPresenter != null){
            mMainPresenter.getAttendant(attendantBO);
        }

        if(mSignUpPresenter != null){

        }

        if(mLoginPresenter != null){

        }
    }

    @Override
    public void saveAttendant(AttendantBO attendantBO) {
        final DatabaseReference dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
        AttendantDocJson attendantDocJson = new AttendantDocJson();
        attendantDocJson.setValues(attendantBO);
        dbRefAttendants.child(attendantDocJson.getUserUid()).setValue(attendantDocJson);

        RefPointDocJson refPointDocJson = new RefPointDocJson();
        refPointDocJson.setValues(attendantBO);
        dbRefAttendants.child(attendantDocJson.getUserUid())
                .child(Firebase.ATTENDANTS_REF_POINT)
                .setValue(refPointDocJson);
    }

}

