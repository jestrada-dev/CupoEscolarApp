package co.jestrada.cupoescolarapp.student.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.common.interactor.DocIdTypeInteractor;
import co.jestrada.cupoescolarapp.common.interactor.GradeInteractor;
import co.jestrada.cupoescolarapp.common.interactor.RelationshipTypeInteractor;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;
import co.jestrada.cupoescolarapp.student.interactor.StudentInteractor;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class AddEditStudentPresenter extends BasePresenter implements
        IAddEditStudentContract.IAddEditStudentPresenter {

    private IAddEditStudentContract.IAddEditStudentView mAddEditStudentView;
    private Context mContext;

    private StudentInteractor mStudentInteractor;
    private DocIdTypeInteractor mDocIdTypeInteractor;
    private RelationshipTypeInteractor mRelationshipTypeInteractor;
    private GradeInteractor mGradeInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private AttendantBO attendantBO;

    public AddEditStudentPresenter(final Context mContext) {
        this.mAddEditStudentView = (IAddEditStudentContract.IAddEditStudentView) mContext;
        this.mStudentInteractor = new StudentInteractor(
                null,
                this,
                null,
                null
        );
        this.mDocIdTypeInteractor = new DocIdTypeInteractor(null,
                this
        );
        this.mRelationshipTypeInteractor = new RelationshipTypeInteractor(
                this
        );
        this.mGradeInteractor = new GradeInteractor(
                this
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        mAddEditStudentView.getStudentTransactionState(successful);
    }

    @Override
    public void getData(String docIdStudent) {
        mDocIdTypeInteractor.getDocIdTypes();
        mRelationshipTypeInteractor.getRelationshipTypes();
        mGradeInteractor.getGrades();
        mStudentInteractor.getStudent(docIdStudent);
    }

    @Override
    public void getRelationshipTypes(ArrayList<RelationshipTypeBO> relationshipTypeBOS, boolean isChanged) {
        mAddEditStudentView.setRelationshipTypesList(relationshipTypeBOS, isChanged);
    }

    @Override
    public void getGrades(ArrayList<GradeBO> gradeBOS, boolean isChanged) {
        mAddEditStudentView.setGradesList(gradeBOS, isChanged);
    }

    @Override
    public void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged) {
        mAddEditStudentView.setDocIdTypesList(docIdTypeBOS, isChanged);
    }

    @Override
    public void getStudent(StudentBO studentBO, boolean isChanged) {
        mAddEditStudentView.setStudentUI(studentBO, isChanged);
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
