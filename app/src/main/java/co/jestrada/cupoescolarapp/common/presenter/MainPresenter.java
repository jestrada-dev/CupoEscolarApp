package co.jestrada.cupoescolarapp.common.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class MainPresenter extends BasePresenter implements
        IMainContract.IMainPresenter{

    private IMainContract.IMainView mMainView;
    private Context mContext;

    private AttendantInteractor mAttendantInteractor;

    private FirebaseAuth mFirebaseAuth;

    private AttendantBO attendantBO;

    public MainPresenter(final Context mContext) {
        this.mMainView = (IMainContract.IMainView) mContext;
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
        attendantBO = attendantBO.getInstance();
        if (!attendantBO.isOnSession()){
            signOut();
        } else {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null){
                mAttendantInteractor.getAttendant(mFirebaseUser.getUid());
            }
        }
    }

    @Override
    public void signOut() {
        attendantBO = AttendantBO.getInstance();
        attendantBO.setOnSession(false);
        mFirebaseAuth.signOut();
        mMainView.goToLogin();
    }

    @Override
    public void getUser(boolean isChanged) {
        mMainView.setNavViewUI(isChanged);
    }

    @Override
    public void getAttendant(boolean isChanged) {

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
