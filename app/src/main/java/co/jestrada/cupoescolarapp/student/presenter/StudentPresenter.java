package co.jestrada.cupoescolarapp.student.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
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

    private AttendantBO attendantBO;

    public StudentPresenter(final Context mContext) {
        this.mStudentView = (IStudentContract.IStudentView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                null,
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        mStudentView.getStudentTransactionState(successful);
    }

    @Override
    public void getData() {
        attendantBO = AttendantBO.getInstance();
        if (attendantBO.getUserUid() != null){
            mStudentInteractor.getStudentsByAttendantUserUid(attendantBO.getUserUid());
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
