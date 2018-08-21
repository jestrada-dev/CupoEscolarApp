package co.jestrada.cupoescolarapp.location.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.location.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;

public class RefPositionPresenter extends BasePresenter implements
        IRefPositionContract.IRefPositionPresenter{

    private IRefPositionContract.IRefPositionView mRefPositionView;
    private Context mContext;

    private RefPositionInteractor mRefPositionInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private AttendantBO attendantBO;

    public RefPositionPresenter(final Context mContext) {
        this.mRefPositionView = (IRefPositionContract.IRefPositionView) mContext;
        this.mRefPositionInteractor = new RefPositionInteractor(
                null,
                this,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {
        mRefPositionView.getRefPositionTransactionState(successful);
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
        attendantBO = AttendantBO.getInstance();
        if (attendantBO != null){
            refPositionBO.setUserUid(attendantBO.getUserUid());
            mRefPositionInteractor.saveRefPosition(refPositionBO);
        }
    }

    @Override
    public void saveDescriptionRefPosition(RefPositionBO refPositionBO) {
        attendantBO = AttendantBO.getInstance();
        if (attendantBO != null){
            refPositionBO.setUserUid(attendantBO.getUserUid());
            mRefPositionInteractor.saveDescriptionRefPosition(refPositionBO);
        }
    }

}
