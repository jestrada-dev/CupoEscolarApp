package co.jestrada.cupoescolarapp.attendant.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class MainPresenter extends BasePresenter implements
        IMainContract.IMainPresenter{

    private IMainContract.IMainView mMainView;
    private Context mContext;

    private UserInteractor mUserInteractor;
    private AttendantInteractor mAttendantInteractor;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public MainPresenter(final Context mContext) {
        this.mMainView = (IMainContract.IMainView) mContext;
        this.mUserInteractor = new UserInteractor(
                null,
                null,
                this);
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                null,
                this,
                null,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    private void getData(){
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            mAttendantInteractor.getAttendant(mFirebaseUser.getUid());
        }

    }

    @Override
    public void signOut() {
        userBOApp = UserBO.getInstance();
        userBOApp.setOnSession(false);
        mFirebaseAuth.signOut();
        mMainView.goToLogin();
    }

    @Override
    public void getUser(UserBO userBO) {

    }

    @Override
    public void getAttendant(AttendantBO attendantBO, boolean isChanged) {
        mMainView.setNavViewUI(attendantBO, isChanged);
    }

    @Override
    public void getRefPosition(RefPositionBO refPositionBO, boolean isChanged) {

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
}
