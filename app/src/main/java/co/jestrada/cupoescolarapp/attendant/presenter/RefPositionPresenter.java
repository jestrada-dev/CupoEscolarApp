package co.jestrada.cupoescolarapp.attendant.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class RefPositionPresenter extends BasePresenter implements
        IRefPositionContract.IRefPositionPresenter{

    private IRefPositionContract.IRefPositionView mRefPositionView;
    private Context mContext;

    private RefPositionInteractor mRefPositionInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private UserBO userBOApp;

    public RefPositionPresenter(final Context mContext) {
        this.mRefPositionView = (IRefPositionContract.IRefPositionView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                this,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        userBOApp = UserBO.getInstance();
    }

    @Override
    public void getData() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mRefPositionInteractor.getRefPosition(mFirebaseUser.getUid());
        }
    }


    @Override
    public void getRefPosition(RefPositionBO refPositionBO, boolean isChanged) {
        mRefPositionView.setRefPositionUI(refPositionBO, isChanged);

    }

    @Override
    public void saveRefPosition(RefPositionBO refPositionBO) {
        userBOApp = UserBO.getInstance();
        if (userBOApp != null){
            refPositionBO.setUserUid(userBOApp.getuId());
            mRefPositionInteractor.saveRefPosition(refPositionBO);
        }
    }

    @Override
    public void saveDescriptionRefPosition(RefPositionBO refPositionBO) {
        userBOApp = UserBO.getInstance();
        if (userBOApp != null){
            refPositionBO.setUserUid(userBOApp.getuId());
            mRefPositionInteractor.saveDescriptionRefPosition(refPositionBO);
        }
    }

}
