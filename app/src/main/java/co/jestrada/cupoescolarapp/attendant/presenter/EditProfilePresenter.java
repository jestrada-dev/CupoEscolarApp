package co.jestrada.cupoescolarapp.attendant.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.contract.IAttendantProfileContract;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPointBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class EditProfilePresenter extends BasePresenter implements
        IAttendantProfileContract.IAttendantProfilePresenter, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_FINE_LOCATION = 123;
    private IAttendantProfileContract.IAttendantProfileView mAttendantProfileView;
    private Context mContext;

    private AttendantInteractor mAttendantInteractor;

    private FusedLocationProviderClient mFusedLocationClient;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public EditProfilePresenter(final Context mContext) {
        this.mAttendantProfileView = (IAttendantProfileContract.IAttendantProfileView) mContext;
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                null,
                this);
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) mContext);

    }

    private void getData() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mAttendantInteractor.getAttendant(mFirebaseUser.getUid());
        }
    }

    @Override
    public void getAttendant(AttendantBO attendantBO) {
        mAttendantProfileView.setAttendantUI(attendantBO);
    }

    @Override
    public void saveAttendant(AttendantBO attendantBO) {
        userBOApp = UserBO.getInstance();
        attendantBO.setUserUid(userBOApp.getuId());
        mAttendantInteractor.saveAttendant(attendantBO);
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
                                mAttendantProfileView.setCoordsCurrentPositionUI(location);
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
        mAttendantProfileView.goToLogin();
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
