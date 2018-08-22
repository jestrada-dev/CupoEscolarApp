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

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.location.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.constant.ConstantsFirebaseAttendant;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.attendant.contract.ILoginContract;
import co.jestrada.cupoescolarapp.attendant.contract.ISignUpContract;

public class AttendantInteractor implements
        IBaseContract.IBaseInteractor{

    private IAppCoreContract.IAppCore mAppCore;
    private ILoginContract.ILoginPresenter mLoginPresenter;
    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;
    private IEditProfileContract.IEditProfilePresenter mEditProfilePresenter;
    private ICurrentPositionMapContract.ICurrentPositionMapPresenter mCurrentPositionMapPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefAttendants;

    AttendantBO attendantBO;

    public AttendantInteractor(@Nullable IAppCoreContract.IAppCore mAppCore,
                               @Nullable ILoginContract.ILoginPresenter mLoginPresenter,
                               @Nullable ISignUpContract.ISignUpPresenter mSignUpPresenter,
                               @Nullable IMainContract.IMainPresenter mMainPresenter,
                               @Nullable IEditProfileContract.IEditProfilePresenter mEditProfilePresenter,
                               @Nullable ICurrentPositionMapContract.ICurrentPositionMapPresenter mCurrentPositionMapPresenter) {
        this.mAppCore = mAppCore;
        this.mLoginPresenter = mLoginPresenter;
        this.mSignUpPresenter = mSignUpPresenter;
        this.mMainPresenter = mMainPresenter;
        this.mEditProfilePresenter = mEditProfilePresenter;
        this.mCurrentPositionMapPresenter = mCurrentPositionMapPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefAttendants = mFirebaseDB.getReference(ConstantsFirebaseAttendant.ATTENDANTS);
    }

    public void getAttendant() {
        attendantBO = AttendantBO.getInstance();
        dbRefAttendants.child(attendantBO.getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                if(attendantDS.exists()){
                    final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);
                    if(attendantDocJson != null){
                        attendantBO = AttendantBO.getInstance();
                        attendantBO.setValues(attendantDocJson);

                        for(DataSnapshot dataSnapshot : attendantDS.child(ConstantsFirebaseAttendant.ATTENDANT_STUDENTS).getChildren()){
                            String studentDocId = dataSnapshot.getValue(String.class);
                            attendantBO.addStudent(studentDocId);
                        }

                        notifyAttendantChanges(true);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void notifyAttendantChanges(boolean isChanged) {

        if(mMainPresenter != null){
            mMainPresenter.getAttendant(isChanged);
        }
        if(mEditProfilePresenter != null){
            mEditProfilePresenter.getAttendant(isChanged);
        }
    }

    private void notifyTransactionState(boolean successful){
        if(mMainPresenter != null){
            mMainPresenter.getAttendantTransactionState(successful);
        }

        if(mEditProfilePresenter != null){
            mEditProfilePresenter.getAttendantTransactionState(successful);
        }
    }

    public void saveAttendant() {
        attendantBO = AttendantBO.getInstance();
        if(attendantBO.getUserUid() != null){
            dbRefAttendants = mFirebaseDB.getReference(ConstantsFirebaseAttendant.ATTENDANTS);
            final AttendantDocJson attendantDocJson = new AttendantDocJson();
            attendantDocJson.setValues(attendantBO);
            final ArrayList<String> studentDocIds = new ArrayList<>();
            studentDocIds.addAll(attendantBO.getStudents());

            dbRefAttendants.child(attendantDocJson.getUserUid()).setValue(attendantDocJson)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                if (studentDocIds.isEmpty()){
                                    notifyTransactionState(task.isSuccessful());
                                }else {
                                    dbRefAttendants.child(attendantDocJson.getUserUid())
                                            .child(ConstantsFirebaseAttendant.ATTENDANT_STUDENTS)
                                            .setValue(studentDocIds).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            notifyTransactionState(task.isSuccessful());
                                        }
                                    });
                                }

                            }

                        }
                    });
        }
    }

    public void activateUser() {
        attendantBO = AttendantBO.getInstance();
        if(attendantBO.getUserUid() != null){
            dbRefAttendants.child(attendantBO.getUserUid())
                    .child(ConstantsFirebaseAttendant.ATTENDANT_FIELD_STATE)
                    .setValue(StateUserEnum.ACTIVE);
        }
    }

}

