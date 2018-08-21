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

import co.jestrada.cupoescolarapp.attendant.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.location.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.constant.ConstantsFirebaseAttendant;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.attendant.constant.ConstantsFirebaseUser;
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

    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                if(!attendantDS.exists()){
                    Log.d("Attendant","AttendantInteractor -> Se ejecutó el onDataChange para " + ConstantsFirebaseAttendant.ATTENDANTS + "/" + userUid + " pero el attendantDS vino null");
                }
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);
                Log.d("Attendant","AttendantInteractor -> Se ejecutó el onDataChange para " + ConstantsFirebaseAttendant.ATTENDANTS + "/" + userUid);

                if(attendantDocJson != null){
                    attendantBO = AttendantBO.getInstance();
                    attendantBO.setValues(attendantDocJson);
                    notifyAttandantChanges(true);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Attendant","AttendantInteractor -> Se ejecutó el onCancelled " + ConstantsFirebaseAttendant.ATTENDANTS + "/" + userUid);

            }
        });
    }

    private void notifyAttandantChanges(boolean isChanged) {

        if(mMainPresenter != null){
            mMainPresenter.getAttendant( isChanged);
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
        if(attendantBO.getUserUid() != null){
            final DatabaseReference dbRefAttendants = mFirebaseDB.getReference(ConstantsFirebaseAttendant.ATTENDANTS);
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
                        notifyAttandantChanges( false);
                    }
                }
            });
        } else {
            Log.d("Attendant","AttendantInteractor -> Usuario null");
        }

    }

    public void activateUser() {
        attendantBO = AttendantBO.getInstance();

        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(ConstantsFirebaseUser.USERS);
        dbRefUsers.child(attendantBO.getUserUid())
                .child(ConstantsFirebaseUser.USER_FIELD_STATE)
                .setValue(StateUserEnum.ACTIVE).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                }
            }
        });

/*        final String strFecha = DateFormat.format(CustomDateUtils.LONG_DATE, new Date()).toString();
        dbRefUsers.child(attendantBO.getUserUid())
                .child(ConstantsFirebaseUser.USER_LOGINS)
                .child(ConstantsFirebaseUser.USER_LOGIN_METHOD_EMAIL_PASSWORD)
                .child(ConstantsFirebaseUser.USER_FIELD_ACTIVATE_TIMESTAMP)
                .setValue(strFecha).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }
            }
        });*/
    }

}

