package co.jestrada.cupoescolarapp.location.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.location.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;

public class CurrentPositionMapPresenter extends BasePresenter implements
        ICurrentPositionMapContract.ICurrentPositionMapPresenter,
        GoogleApiClient.OnConnectionFailedListener{

    private RefPositionInteractor mRefPositionInteractor;
    private ICurrentPositionMapContract.ICurrentPositionMapView mCurrentPositionMapView;
    private Context mContext;

    private FirebaseAuth mFirebaseAuth;

    private AttendantBO attendantBO;

    public CurrentPositionMapPresenter(final Context mContext) {
        this.mCurrentPositionMapView = (ICurrentPositionMapContract.ICurrentPositionMapView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                null,
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
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
        attendantBO = AttendantBO.getInstance();
        refPositionBO.setUserUid(attendantBO.getUserUid());
        mRefPositionInteractor.saveRefPosition(refPositionBO);
    }

    @Override
    public void saveRefPositionNoDescription(RefPositionBO refPositionBO) {
        attendantBO = AttendantBO.getInstance();
        refPositionBO.setUserUid(attendantBO.getUserUid());
        mRefPositionInteractor.saveRefPositionNoDescription(refPositionBO);
    }

    @Override
    public void onStart() {
        attendantBO = AttendantBO.getInstance();
        attendantBO = AttendantBO.getInstance();
        if (!attendantBO.isOnSession()){
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
