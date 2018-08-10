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
import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.interactor.DocIdTypeInteractor;
import co.jestrada.cupoescolarapp.attendant.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class RefPositionPresenter extends BasePresenter implements
        IRefPositionContract.IRefPositionPresenter,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_FINE_LOCATION = 123;
    private IRefPositionContract.IRefPositionView mRefPositionView;
    private Context mContext;

    private RefPositionInteractor mRefPositionInteractor;

    private FusedLocationProviderClient mFusedLocationClient;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public RefPositionPresenter(final Context mContext) {
        this.mRefPositionView = (IRefPositionContract.IRefPositionView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                null,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) mContext);
        userBOApp = UserBO.getInstance();
    }

    private void getData() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mRefPositionInteractor.getRefPosition(mFirebaseUser.getUid());
        }
    }


/*    public void getCoordsCurrentPosition() {
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
                                mRefPositionView.setRefPositionUI(location);
                            }
                        }
                    });

        }
    }*/

    @Override
    public void getRefPosition(RefPositionBO refPositionBO) {

    }

    @Override
    public void saveRefPosition(RefPositionBO refPositionBO) {

    }

    @Override
    public void onStart() {
        userBOApp = UserBO.getInstance();
        if (!userBOApp.isOnSession()){
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
