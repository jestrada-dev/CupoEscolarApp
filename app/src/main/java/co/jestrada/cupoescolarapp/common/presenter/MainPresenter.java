package co.jestrada.cupoescolarapp.common.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

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


    public void getData(){
        userBOApp = UserBO.getInstance();
        if (!userBOApp.isOnSession()){
            signOut();
        } else {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null){
                mUserInteractor.getUser(mFirebaseUser.getUid());
            }
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
    public void getUser(UserBO userBO, boolean isChanged) {
        mMainView.setNavViewUI(userBO, isChanged);
    }

    @Override
    public void getAttendant(AttendantBO attendantBO, boolean isChanged) {

    }

    @Override
    public void getRefPosition(RefPositionBO refPositionBO, boolean isChanged) {

    }

    @Override
    public void getStudent(StudentBO studentBO, boolean isChanged) {

    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {

    }

    @Override
    public void getAttendantTransactionState(boolean successful) {
        mMainView.getAttendantTransactionState(successful);
    }

    @Override
    public void getStudentTransactionState(boolean successful) {

    }

}
