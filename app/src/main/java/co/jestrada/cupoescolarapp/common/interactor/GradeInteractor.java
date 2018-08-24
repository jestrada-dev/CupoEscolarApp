package co.jestrada.cupoescolarapp.common.interactor;

import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.constant.ConstantsFirebaseGrade;
import co.jestrada.cupoescolarapp.common.constant.ConstantsFirebaseRelationshipType;
import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.common.model.modelDocJson.GradeDocJson;
import co.jestrada.cupoescolarapp.common.model.modelDocJson.RelationshipTypeDocJson;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;

public class GradeInteractor implements
        IBaseContract.IBaseInteractor{

    private IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefGrades;

    public GradeInteractor(@Nullable IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter) {
        this.mAddEditStudentPresenter = mAddEditStudentPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefGrades = mFirebaseDB.getReference(ConstantsFirebaseGrade.GRADE);
    }

    public void getGrades() {
        dbRefGrades.child(ConstantsFirebaseGrade.GRADE_SPANISH)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot gradesDS) {
                ArrayList<GradeBO> gradeBOS = new ArrayList<>();
                for( DataSnapshot gradeChildDS : gradesDS.getChildren()){
                    GradeDocJson gradeDocJson = gradeChildDS.getValue(GradeDocJson.class);
                    GradeBO gradeBO = new GradeBO();
                    gradeBO.setValues(gradeDocJson);
                    gradeBOS.add(gradeBO);
                }
                notifyGradesChanges(gradeBOS, true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyGradesChanges(ArrayList<GradeBO> gradeBOS, boolean isChanged) {
        if(mAddEditStudentPresenter != null){
            mAddEditStudentPresenter.getGrades(gradeBOS, isChanged);
        }
    }

}

