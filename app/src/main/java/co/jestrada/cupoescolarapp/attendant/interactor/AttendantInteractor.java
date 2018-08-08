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

import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPointDocJson;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;

public class AttendantInteractor implements
        IBaseContract.IBaseInteractor,
        ISignUpContract.IAttendantInteractor{

    private IAppCoreContract.IAppCore mAppCore;
    private ISignUpContract.ISignUpPresenter mSignUpPresenter;
    private IMainContract.IMainPresenter mMainPresenter;
    private ILoginContract.ILoginPresenter mLoginPresenter;
    private IEditProfileContract.IEditProfilePresenter mEditProfilePresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefAttendants;

    public AttendantInteractor(@Nullable IAppCoreContract.IAppCore mAppCore,
                               @Nullable ISignUpContract.ISignUpPresenter mSignUpPresenter,
                               @Nullable IMainContract.IMainPresenter mMainPresenter,
                               @Nullable ILoginContract.ILoginPresenter mLoginPresenter,
                               @Nullable IEditProfileContract.IEditProfilePresenter mEditProfilePresenter) {
        this.mAppCore = mAppCore;
        this.mSignUpPresenter = mSignUpPresenter;
        this.mMainPresenter = mMainPresenter;
        this.mLoginPresenter = mLoginPresenter;
        this.mEditProfilePresenter = mEditProfilePresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefAttendants = mFirebaseDB.getReference(Firebase.ATTENDANTS);
    }

    @Override
    public void getAttendant(final String userUid) {
        dbRefAttendants.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attendantDS) {
                final AttendantDocJson attendantDocJson = attendantDS.getValue(AttendantDocJson.class);
                Log.d("Attendant","AttendantInteractor -> Se ejecutó el onDataChange para " + Firebase.ATTENDANTS + "/" + userUid);

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

    @Override
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

                        if(attendantBO.getRefPoint() != null){
                            dbRefAttendants.child(attendantDocJson.getUserUid())
                                    .child(Firebase.ATTENDANTS_REF_POINT)
                                    .setValue(attendantBO.getRefPoint()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("Attendant","AttendantInteractor -> Usuario: email:" + attendantBO.getEmail() +
                                                " RefPoint Lat: " + attendantBO.getRefPoint().getLat() + " grabado exitosamente");

                                    }
                                }
                            });
                        }

                    }
                }
            });
        } else {
            Log.d("Attendant","AttendantInteractor -> Usuario null");
        }

    }

}

