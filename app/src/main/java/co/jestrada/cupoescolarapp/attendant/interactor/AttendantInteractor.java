package co.jestrada.cupoescolarapp.attendant.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.jestrada.cupoescolarapp.attendant.contract.IAttendantProfileContract;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPointBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPointDocJson;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;

public class AttendantInteractor implements
        IBaseContract.IBaseInteractor,
        ISignUpContract.IAttendantInteractor{

    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;
    private ILoginContract.ILoginPresenter mLoginPresenter;
    private IAttendantProfileContract.IAttendantProfilePresenter mAttendantProfilePresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefAttendants;

    public AttendantInteractor(@Nullable ISignUpContract.ISignUpPresenter mSignUpPresenter,
                               @Nullable IMainContract.IMainPresenter mMainPresenter,
                               @Nullable ILoginContract.ILoginPresenter mLoginPresenter,
                               @Nullable IAttendantProfileContract.IAttendantProfilePresenter mAttendantProfilePresenter) {
        this.mSignUpPresenter = mSignUpPresenter;
        this.mMainPresenter = mMainPresenter;
        this.mLoginPresenter = mLoginPresenter;
        this.mAttendantProfilePresenter = mAttendantProfilePresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
    }

    @Override
    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);
                if(attendantDocJson != null){
                    final RefPointDocJson refPointDocJson = attendantDS.child(Firebase.ATTENDANTS_REF_POINT).getValue(RefPointDocJson.class);
                    AttendantBO attendantBO = new AttendantBO();
                    attendantBO.setValues(attendantDocJson, refPointDocJson);
                    notifyAttandantChanges(attendantBO);
                }
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

        if(mAttendantProfilePresenter != null){
            mAttendantProfilePresenter.getAttendant(attendantBO);
        }
    }

    @Override
    public void saveAttendant(final AttendantBO attendantBO) {
        if(attendantBO != null){
            final DatabaseReference dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
            final AttendantDocJson attendantDocJson = new AttendantDocJson();
            attendantDocJson.setValues(attendantBO);
            dbRefAttendants.child(attendantDocJson.getUserUid())
                    .setValue(attendantDocJson).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        if(attendantBO.getRefPoint() != null){
                            dbRefAttendants.child(attendantDocJson.getUserUid())
                                    .child(Firebase.ATTENDANTS_REF_POINT)
                                    .setValue(attendantBO.getRefPoint());
                        }

                    }
                }
            });
        }

    }

}

