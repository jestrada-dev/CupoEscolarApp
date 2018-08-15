package co.jestrada.cupoescolarapp.location.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.location.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.location.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class CurrentPositionMapPresenter extends BasePresenter implements
        ICurrentPositionMapContract.ICurrentPositionMapPresenter,
        GoogleApiClient.OnConnectionFailedListener{

    private RefPositionInteractor mRefPositionInteractor;
    private ICurrentPositionMapContract.ICurrentPositionMapView mCurrentPositionMapView;
    private Context mContext;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public CurrentPositionMapPresenter(final Context mContext) {
        this.mCurrentPositionMapView = (ICurrentPositionMapContract.ICurrentPositionMapView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                null,
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        userBOApp = UserBO.getInstance();
    }

    private void getData() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mRefPositionInteractor.getRefPosition(mFirebaseUser.getUid());
        }
    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {
        mCurrentPositionMapView.getRefPositionTransactionState(successful);
    }

    @Override
    public void getRefPosition(RefPositionBO refPositionBO, boolean isChanged) {
        mCurrentPositionMapView.setRefPosition(refPositionBO, isChanged);
    }

    @Override
    public void saveRefPosition(RefPositionBO refPositionBO) {
        userBOApp = UserBO.getInstance();
        refPositionBO.setUserUid(userBOApp.getuId());
        mRefPositionInteractor.saveRefPosition(refPositionBO);
    }

    @Override
    public void saveRefPositionNoDescription(RefPositionBO refPositionBO) {
        userBOApp = UserBO.getInstance();
        refPositionBO.setUserUid(userBOApp.getuId());
        mRefPositionInteractor.saveRefPositionNoDescription(refPositionBO);
    }

    @Override
    public void onStart() {
        userBOApp = UserBO.getInstance();
        userBOApp = UserBO.getInstance();
        if (!userBOApp.isOnSession()){
        } else {
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
