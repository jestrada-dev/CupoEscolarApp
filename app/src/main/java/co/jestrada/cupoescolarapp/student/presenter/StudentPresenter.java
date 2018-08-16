package co.jestrada.cupoescolarapp.student.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.student.contract.IEditStudentContract;
import co.jestrada.cupoescolarapp.student.contract.IStudentContract;
import co.jestrada.cupoescolarapp.student.interactor.StudentInteractor;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class StudentPresenter extends BasePresenter implements
        IStudentContract.IStudentPresenter{

    private IStudentContract.IStudentView mStudentView;
    private Context mContext;

    private StudentInteractor mStudentInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private UserBO userBOApp;

    public StudentPresenter(final Context mContext) {
        this.mStudentView = (IStudentContract.IStudentView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                null,
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        userBOApp = UserBO.getInstance();
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        mStudentView.getStudentTransactionState(successful);
    }

    @Override
    public void getData() {
        userBOApp = UserBO.getInstance();
        if (userBOApp.getuId() != null){
            mStudentInteractor.getStudentsByAttendantUserUid(userBOApp.getuId());
        }
    }

    @Override
    public void getStudent(StudentBO studentBO, boolean isChanged) {

    }

    @Override
    public void getStudentsByAttendantUserUid(ArrayList<StudentBO> studentBOS, boolean isChanged) {
        mStudentView.setStudentsUI(studentBOS, isChanged);
    }

    @Override
    public void saveStudent(StudentBO studentBO) {

    }

}
