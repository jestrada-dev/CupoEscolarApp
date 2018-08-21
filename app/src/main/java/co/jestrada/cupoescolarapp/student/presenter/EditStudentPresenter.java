package co.jestrada.cupoescolarapp.student.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
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

    private AttendantBO attendantBO;

    public EditStudentPresenter(final Context mContext) {
        this.mEditStudentView = (IEditStudentContract.IEditStudentView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                this,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
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
            attendantBO = AttendantBO.getInstance();
            if (attendantBO != null){
                studentBO.setAttendantUserUid(attendantBO.getUserUid());
            }
        }
        mStudentInteractor.saveStudent(studentBO);
    }

}
