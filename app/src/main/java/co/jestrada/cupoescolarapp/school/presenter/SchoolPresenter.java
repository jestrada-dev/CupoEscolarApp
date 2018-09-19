package co.jestrada.cupoescolarapp.school.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.common.interactor.DocIdTypeInteractor;
import co.jestrada.cupoescolarapp.common.interactor.GradeInteractor;
import co.jestrada.cupoescolarapp.common.interactor.RelationshipTypeInteractor;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.school.contract.SchoolContract;
import co.jestrada.cupoescolarapp.school.interactor.SchoolInteractor;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;
import co.jestrada.cupoescolarapp.student.interactor.StudentInteractor;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class SchoolPresenter extends BasePresenter implements
        SchoolContract.ISchoolPresenter {

    private SchoolContract.ISchoolView mSchoolView;
    private Context mContext;

    private SchoolInteractor mSchoolInteractor;
    private StudentInteractor mStudentInteractor;

    ArrayList<StudentBO> students;

    private AttendantBO attendantBO;

    public SchoolPresenter(final Context mContext) {
        this.mSchoolView = (SchoolContract.ISchoolView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                null,
                null,
                this
        );
        this.mSchoolInteractor = new SchoolInteractor(null,
                this
        );

        this.mContext = mContext;
        attendantBO = AttendantBO.getInstance();

        students = new ArrayList<>();
    }

    public void enrollStudent(String schoolCode, String studentDocId){
        mStudentInteractor.enrollStudent(studentDocId, schoolCode);
    }

    @Override
    public void getEnrollStudentTransactionState(boolean succesful) {
        mSchoolView.getEnrollStudentTransactionState(succesful);
    }

    @Override
    public void getData(String schoolCode) {
        if(attendantBO != null){
            mSchoolInteractor.getSchoolsByCode(schoolCode);
            mStudentInteractor.getStudentsByAttendantUserUid(attendantBO.getUserUid());
        }
    }

    @Override
    public void getSchool(SchoolBO schoolBO, boolean isChanged) {
        mSchoolView.setSchoolUI(schoolBO, isChanged);
    }

    @Override
    public void getStudents(ArrayList<StudentBO> studentBOS, boolean isChanged) {
            mSchoolView.setStudentList(studentBOS, isChanged);
            if(isChanged){
                students = studentBOS;
            }
    }

}
