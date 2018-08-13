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

import java.sql.Ref;

import co.jestrada.cupoescolarapp.attendant.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPositionDocJson;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;

public class RefPositionInteractor implements
        IBaseContract.IBaseInteractor{

    private IMainContract.IMainPresenter mMainPresenter;
    private IRefPositionContract.IRefPositionPresenter mRefPositionPresenter;
    private ICurrentPositionMapContract.ICurrentPositionMapPresenter mCurrentPositionMapPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefRefPositions;

    public RefPositionInteractor(@Nullable IMainContract.IMainPresenter mMainPresenter,
                                 @Nullable IRefPositionContract.IRefPositionPresenter mRefPositionPresenter,
                                 @Nullable ICurrentPositionMapContract.ICurrentPositionMapPresenter mCurrentPositionMapPresenter) {

        this.mMainPresenter = mMainPresenter;
        this.mRefPositionPresenter = mRefPositionPresenter;
        this.mCurrentPositionMapPresenter = mCurrentPositionMapPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefRefPositions = mFirebaseDB.getReference(Firebase.REF_POSITIONS);
    }

    public void getRefPosition(final String userUid) {

        dbRefRefPositions.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot refPositionDS) {
                final RefPositionDocJson refPositionDocJson = refPositionDS.getValue(RefPositionDocJson.class);
                Log.d("RefPositions","RefPositionInteractor -> Se ejecutó el onDataChange para " + Firebase.REF_POSITIONS + "/" + userUid);

                if(refPositionDocJson != null){
                    RefPositionBO refPositionBO = new RefPositionBO();
                    refPositionBO.setValues(refPositionDocJson);
                    notifyRefPositionChanges(refPositionBO, true);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyRefPositionChanges(RefPositionBO refPositionBO, boolean isChanged) {
        if(mMainPresenter != null){
            mMainPresenter.getRefPosition(refPositionBO, isChanged);
        }
        if(mRefPositionPresenter != null){
            mRefPositionPresenter.getRefPosition(refPositionBO, isChanged);
        }

        if (mCurrentPositionMapPresenter != null){
            mCurrentPositionMapPresenter.getRefPosition(refPositionBO, isChanged);
        }
    }

    public void saveRefPosition(final RefPositionBO refPositionBO) {
        if(refPositionBO.getUserUid() != null){
            final DatabaseReference dbRefRefPositions = mFirebaseDB.getReference(Firebase.REF_POSITIONS);
            final RefPositionDocJson refPositionDocJson = new RefPositionDocJson();
            refPositionDocJson.setValues(refPositionBO);
            Log.d("RefPosition","RefPositionInteractor -> Usuario: " + refPositionDocJson.getUserUid());
            dbRefRefPositions.child(refPositionDocJson.getUserUid())
                    .setValue(refPositionBO).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("RefPosition","RefPositionInteractor -> Usuario: email:" + refPositionBO.getUserUid() +
                                " grabado exitosamente");
                        notifyRefPositionChanges(refPositionBO, false);
                    }
                }
            });
        } else {
            Log.d("RefPosition","RefPositionInteractor -> Usuario null");
        }

    }


    public void saveDescriptionRefPosition(final RefPositionBO refPositionBO) {
        if(refPositionBO.getUserUid() != null){
            final DatabaseReference dbRefRefPositions = mFirebaseDB.getReference(Firebase.REF_POSITIONS);
            final RefPositionDocJson refPositionDocJson = new RefPositionDocJson();
            refPositionDocJson.setValues(refPositionBO);
            Log.d("RefPosition","RefPositionInteractor -> Usuario: " + refPositionDocJson.getUserUid());
            dbRefRefPositions.child(refPositionDocJson.getUserUid())
                    .child(Firebase.REF_POSITIONS_DESCRIPTION)
                    .setValue(refPositionDocJson.getDescription()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("RefPosition","RefPositionInteractor -> Usuario: email:" + refPositionBO.getUserUid() +
                                " grabado exitosamente");
                        getRefPosition(refPositionBO.getUserUid());
                    }
                }
            });
        } else {
            Log.d("RefPosition","RefPositionInteractor -> Usuario null");
        }

    }

    public void saveRefPositionNoDescription(final RefPositionBO refPositionBO) {
        if(refPositionBO.getUserUid() != null){
            final DatabaseReference dbRefRefPositions = mFirebaseDB.getReference(Firebase.REF_POSITIONS);
            Log.d("RefPosition","RefPositionInteractor -> Usuario: " + refPositionBO.getUserUid());
            dbRefRefPositions.child(refPositionBO.getUserUid())
                    .child(Firebase.REF_POSITIONS_ADDRESS)
                    .setValue(refPositionBO.getAddress()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        saveRefPositionLat(refPositionBO);
                    }
                }
            });
        } else {
            Log.d("RefPosition","RefPositionInteractor -> Usuario null");
        }

    }

    private void saveRefPositionLat(final RefPositionBO refPositionBO) {
        dbRefRefPositions.child(refPositionBO.getUserUid())
                .child(Firebase.REF_POSITIONS_LAT)
                .setValue(refPositionBO.getLat()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    saveRefPositionLng(refPositionBO);
                }
            }
        });
    }

    private void saveRefPositionLng(final RefPositionBO refPositionBO) {
        dbRefRefPositions.child(refPositionBO.getUserUid())
                .child(Firebase.REF_POSITIONS_LNG)
                .setValue(refPositionBO.getLng()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    saveRefPositionPostalCode(refPositionBO);
                }
            }
        });
    }

    private void saveRefPositionPostalCode(final RefPositionBO refPositionBO) {
        dbRefRefPositions.child(refPositionBO.getUserUid())
                .child(Firebase.REF_POSITIONS_POSTAL_CODE)
                .setValue(refPositionBO.getPostalCode()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    saveRefPositionCity(refPositionBO);
                }
            }
        });
    }

    private void saveRefPositionCity(final RefPositionBO refPositionBO) {
        dbRefRefPositions.child(refPositionBO.getUserUid())
                .child(Firebase.REF_POSITIONS_CITY)
                .setValue(refPositionBO.getCity()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    saveRefPositionAdminArea(refPositionBO);
                }
            }
        });
    }

    private void saveRefPositionAdminArea(final RefPositionBO refPositionBO) {
        dbRefRefPositions.child(refPositionBO.getUserUid())
                .child(Firebase.REF_POSITIONS_ADMIN_AREA)
                .setValue(refPositionBO.getAdminArea()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("RefPosition","RefPositionInteractor -> Usuario: email:" + refPositionBO.getUserUid() +
                            " grabado exitosamente");
                    notifyRefPositionChanges(refPositionBO, false);
                }
            }
        });
    }

}

