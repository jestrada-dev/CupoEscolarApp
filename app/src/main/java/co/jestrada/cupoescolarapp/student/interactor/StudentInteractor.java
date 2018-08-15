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

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.location.constant.ConstantsFirebaseRefPosition;
import co.jestrada.cupoescolarapp.student.constant.ConstantsFirebaseStudent;
import co.jestrada.cupoescolarapp.student.contract.IEditStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.model.modelDocJson.StudentDocJson;

public class StudentInteractor implements
        IBaseContract.IBaseInteractor{

    private IMainContract.IMainPresenter mMainPresenter;
    private IEditStudentContract.IEditStudentPresenter mEditStudentPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefStudents;

    public StudentInteractor(@Nullable IMainContract.IMainPresenter mMainPresenter,
                             @Nullable IEditStudentContract.IEditStudentPresenter mEditStudentPresenter) {

        this.mMainPresenter = mMainPresenter;
        this.mEditStudentPresenter = mEditStudentPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefStudents = mFirebaseDB.getReference(ConstantsFirebaseStudent.STUDENTS);
    }

    public void getStudent(final String docId) {

        dbRefStudents.child(docId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot studentDS) {
                final StudentDocJson studentDocJson = studentDS.getValue(StudentDocJson.class);
                Log.d("RefPositions","RefPositionInteractor -> Se ejecutó el onDataChange para " + ConstantsFirebaseRefPosition.REF_POSITIONS + "/" + docId);

                if(studentDocJson != null){
                    StudentBO studentBO = new StudentBO();
                    studentBO.setValues(studentDocJson);
                    notifyStudentChanges(studentBO, true);
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
        if(mEditStudentPresenter != null){
            mEditStudentPresenter.getStudent(studentBO, isChanged);
        }
    }

    private void notifyStudentTransactionState(boolean successful){
        if(mMainPresenter != null){
            mMainPresenter.getStudentTransactionState(successful);
        }
        if(mEditStudentPresenter != null){
            mEditStudentPresenter.getStudentTransactionState(successful);
        }

    }

    public void saveStudent(final StudentBO studentBO) {

        if(studentBO.getAttendantUserUid() != null){
            final DatabaseReference dbRefRefPositions = mFirebaseDB.getReference(ConstantsFirebaseRefPosition.REF_POSITIONS);
            final StudentDocJson studentDocJson = new StudentDocJson();
            studentDocJson.setValues(studentBO);
            Log.d("RefPosition","RefPositionInteractor -> Usuario: " + studentDocJson.getAttendantUserUid());
            dbRefRefPositions.child(studentDocJson.getAttendantUserUid())
                    .setValue(studentBO).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    notifyStudentTransactionState(task.isSuccessful());
                    Log.d("RefPosition","RefPositionInteractor -> Usuario: email:" + studentBO.getAttendantUserUid() +
                            " grabado exitosamente");
                }
            });
        } else {
            Log.d("RefPosition","RefPositionInteractor -> Usuario null");
        }

    }

}

