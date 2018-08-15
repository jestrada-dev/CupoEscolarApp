package co.jestrada.cupoescolarapp.student.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.student.contract.IEditStudentContract;
import co.jestrada.cupoescolarapp.student.interactor.StudentInteractor;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class EditStudentPresenter extends BasePresenter implements
        IEditStudentContract.IEditStudentPresenter{

    private IEditStudentContract.IEditStudentView mEditStudentView;
    private Context mContext;

    private StudentInteractor mStudentInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private UserBO userBOApp;

    public EditStudentPresenter(final Context mContext) {
        this.mEditStudentView = (IEditStudentContract.IEditStudentView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        userBOApp = UserBO.getInstance();
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        mEditStudentView.getStudentTransactionState(successful);
    }

    @Override
    public void getData(String docId) {
        mStudentInteractor.getStudent(mFirebaseUser.getUid());
    }

    @Override
    public void getStudent(StudentBO studentBO, boolean isChanged) {
        mEditStudentView.setStudentUI(studentBO, isChanged);
    }

    @Override
    public void saveStudent(StudentBO studentBO) {
        if(studentBO.getAttendantUserUid() == null){
            userBOApp = UserBO.getInstance();
            if (userBOApp != null){
                studentBO.setAttendantUserUid(userBOApp.getuId());
            }
        }
        mStudentInteractor.saveStudent(studentBO);
    }

}
