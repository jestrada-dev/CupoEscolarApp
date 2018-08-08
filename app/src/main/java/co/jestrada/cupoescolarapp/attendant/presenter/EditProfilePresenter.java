package co.jestrada.cupoescolarapp.attendant.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.interactor.DocIdTypeInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class EditProfilePresenter extends BasePresenter implements
        IEditProfileContract.IEditProfilePresenter, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_FINE_LOCATION = 123;
    private IEditProfileContract.IEditProfileView mEditProfileView;
    private Context mContext;

    private DocIdTypeInteractor mDocIdTypeInteractor;
    private AttendantInteractor mAttendantInteractor;

    private FusedLocationProviderClient mFusedLocationClient;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public EditProfilePresenter(final Context mContext) {
        this.mEditProfileView = (IEditProfileContract.IEditProfileView) mContext;
        this.mDocIdTypeInteractor = new DocIdTypeInteractor(this);
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                null,
                null,
                this);
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) mContext);
        userBOApp = UserBO.getInstance();
    }

    private void getData() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mDocIdTypeInteractor.getDocIdTypes();
            mAttendantInteractor.getAttendant(mFirebaseUser.getUid());
        }
    }

    @Override
    public void getAttendant(AttendantBO attendantBO) {
        mEditProfileView.setAttendantUI(attendantBO);
    }

    @Override
    public void saveAttendant(AttendantBO attendantBO) {
        userBOApp = UserBO.getInstance();
        if (userBOApp != null){
            attendantBO.setUserUid(userBOApp.getuId());
            mAttendantInteractor.saveAttendant(attendantBO);
        }
    }

    @Override
    public void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS) {
        if (!docIdTypeBOS.isEmpty()){
            mEditProfileView.setDocIdTypesList(docIdTypeBOS);
        }
    }

    @Override
    public void getCoordsCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

        }else{
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mEditProfileView.setCoordsCurrentPositionUI(location);
                            }
                        }
                    });

        }
    }

    @Override
    public void signOut() {
        userBOApp = UserBO.getInstance();
        userBOApp.setOnSession(false);
        mFirebaseAuth.signOut();
        mEditProfileView.goToLogin();
    }

    @Override
    public void onStart() {
        userBOApp = UserBO.getInstance();
        if (!userBOApp.isOnSession()){
            signOut();
        } else {
            getData();
        }

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
