package co.jestrada.cupoescolarapp.attendant.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.jestrada.cupoescolarapp.attendant.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPositionDocJson;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;

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
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
    }

    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                if(!attendantDS.exists()){
                    Log.d("Attendant","AttendantInteractor -> Se ejecutó el onDataChange para " + Firebase.ATTENDANTS + "/" + userUid + " pero el attendantDS vino null");
                }
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);
                Log.d("Attendant","AttendantInteractor -> Se ejecutó el onDataChange para " + Firebase.ATTENDANTS + "/" + userUid);

                if(attendantDocJson != null){
                    AttendantBO attendantBO = new AttendantBO();
                    attendantBO.setValues(attendantDocJson);
                    notifyAttandantChanges(attendantBO);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Attendant","AttendantInteractor -> Se ejecutó el onCancelled " + Firebase.ATTENDANTS + "/" + userUid);

            }
        });
    }

    private void notifyAttandantChanges(AttendantBO attendantBO) {
        if(mAppCore != null){
            mAppCore.getAttendant(attendantBO);
        }
        if(mMainPresenter != null){
            mMainPresenter.getAttendant(attendantBO);
        }
        if(mEditProfilePresenter != null){
            mEditProfilePresenter.getAttendant(attendantBO);
        }
    }

    public void saveAttendant(final AttendantBO attendantBO) {
        if(attendantBO.getUserUid() != null){
            final DatabaseReference dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
            final AttendantDocJson attendantDocJson = new AttendantDocJson();
            attendantDocJson.setValues(attendantBO);
            Log.d("Attendant","AttendantInteractor -> Usuario: " + attendantDocJson.getUserUid());
            dbRefAttendants.child(attendantDocJson.getUserUid())
                    .setValue(attendantDocJson).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("Attendant","AttendantInteractor -> Usuario: email:" + attendantBO.getEmail() +
                                " grabado exitosamente");
                        notifyAttandantChanges(attendantBO);
                    }
                }
            });
        } else {
            Log.d("Attendant","AttendantInteractor -> Usuario null");
        }

    }

}

