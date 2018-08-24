package co.jestrada.cupoescolarapp.student.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.location.constant.ConstantsFirebaseRefPosition;
import co.jestrada.cupoescolarapp.student.constant.ConstantsFirebaseStudent;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;
import co.jestrada.cupoescolarapp.student.contract.IStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.model.modelDocJson.StudentDocJson;

public class StudentInteractor implements
        IBaseContract.IBaseInteractor{

    private IMainContract.IMainPresenter mMainPresenter;
    private IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter;
    private IStudentContract.IStudentPresenter mStudentPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefStudents;

    public StudentInteractor(@Nullable IMainContract.IMainPresenter mMainPresenter,
                             @Nullable IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter,
                             @Nullable IStudentContract.IStudentPresenter mStudentPresenter) {

        this.mMainPresenter = mMainPresenter;
        this.mAddEditStudentPresenter = mAddEditStudentPresenter;
        this.mStudentPresenter = mStudentPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefStudents = mFirebaseDB.getReference(ConstantsFirebaseStudent.STUDENTS);
    }

    public void getStudent(final String docId) {
        if (docId != null){
            dbRefStudents.child(docId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot studentDS) {
                    final StudentDocJson studentDocJson = studentDS.getValue(StudentDocJson.class);
                    if(studentDocJson != null){
                        StudentBO studentBO = new StudentBO();
                        studentBO.setValues(studentDocJson);
                        notifyStudentChanges(studentBO, true);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyStudentTransactionState(false);
                }
            });

        }
    }

    public void getStudentsByAttendantUserUid(final String attendantUserUid) {
        dbRefStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot studentDS) {
                ArrayList<StudentDocJson> studentDocJsons = new ArrayList<>();
                for(DataSnapshot studentDSChildren: studentDS.getChildren()){
                    final StudentDocJson studentDocJson = studentDSChildren.getValue(StudentDocJson.class);
                    if(studentDocJson.getAttendantUserUid().equals(attendantUserUid)){
                        studentDocJsons.add(studentDocJson);
                    }
                }

                if(!studentDocJsons.isEmpty()){
                    ArrayList<StudentBO> studentBOS = new ArrayList<>();
                    for(StudentDocJson studentDocJson : studentDocJsons){
                        StudentBO studentBO = new StudentBO();
                        studentBO.setValues(studentDocJson);
                        studentBOS.add(studentBO);
                    }
                    notifyStudentsByAttendantUserUidChanges(studentBOS, true);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void notifyStudentChanges(StudentBO studentBO, boolean isChanged) {
        if(mMainPresenter != null){
            mMainPresenter.getStudent(studentBO, isChanged);
        }
        if(mAddEditStudentPresenter != null){
            mAddEditStudentPresenter.getStudent(studentBO, isChanged);
        }
        if(mStudentPresenter != null){
            mStudentPresenter.getStudent(studentBO, isChanged);
        }
    }

    private void notifyStudentsByAttendantUserUidChanges(ArrayList<StudentBO> studentBOS, boolean isChanged) {
        if(mStudentPresenter != null){
            mStudentPresenter.getStudentsByAttendantUserUid(studentBOS, isChanged);
        }

    }

    private void notifyStudentTransactionState(boolean successful){
        if(mMainPresenter != null){
            mMainPresenter.getStudentTransactionState(successful);
        }
        if(mAddEditStudentPresenter != null){
            mAddEditStudentPresenter.getStudentTransactionState(successful);
        }
        if(mStudentPresenter != null){
            mStudentPresenter.getStudentTransactionState(successful);
        }
    }

    public void saveStudent(final StudentBO studentBO) {
            dbRefStudents = mFirebaseDB.getReference(ConstantsFirebaseStudent.STUDENTS);
            final StudentDocJson studentDocJson = new StudentDocJson();
            studentDocJson.setValues(studentBO);
            dbRefStudents.child(studentDocJson.getDocId())
                    .setValue(studentBO).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getStudent(studentBO.getDocId());
                    notifyStudentTransactionState(task.isSuccessful());
                }
            });
        }

}

