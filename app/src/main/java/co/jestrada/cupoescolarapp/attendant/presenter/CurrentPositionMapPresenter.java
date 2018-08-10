package co.jestrada.cupoescolarapp.attendant.presenter;

import android.content.Context;

import co.jestrada.cupoescolarapp.attendant.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class CurrentPositionMapPresenter extends BasePresenter implements
        ICurrentPositionMapContract.ICurrentPositionMapPresenter{

    private RefPositionInteractor mRefPositionInteractor;
    private ICurrentPositionMapContract.ICurrentPositionMapView mCurrentPositionMapView;
    private Context mContext;

    private UserBO userBOApp;

    public CurrentPositionMapPresenter(Context mContext) {
        this.mCurrentPositionMapView = (ICurrentPositionMapContract.ICurrentPositionMapView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                null,
                this
        );
        this.mContext = mContext;

        userBOApp = UserBO.getInstance();
    }


    @Override
    public void getRefPosition(RefPositionBO refPositionBO) {

    }

    @Override
    public void saveRefPosition(RefPositionBO refPositionBO) {
        userBOApp = UserBO.getInstance();
        refPositionBO.setUserUid(userBOApp.getuId());
        mRefPositionInteractor.saveRefPosition(refPositionBO);
    }

    @Override
    public void onStart() {
        userBOApp = UserBO.getInstance();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

}
