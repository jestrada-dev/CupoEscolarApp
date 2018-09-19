package co.jestrada.cupoescolarapp.school.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.jestrada.cupoescolarapp.attendant.constant.ConstantsFirebaseAttendant;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.school.constant.ConstantsFirebaseSchool;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;
import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolDocJson;
import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolOrderedByRefPositionDocJson;

public class SchoolListOrderedInteractor implements IBaseContract.IBaseInteractor{

    private IMainContract.IMainPresenter mMainPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefSchools;

    AttendantBO attendantBO;

    public SchoolListOrderedInteractor(@Nullable IMainContract.IMainPresenter mMainPresenter) {
        this.mMainPresenter = mMainPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefSchools = mFirebaseDB.getReference(ConstantsFirebaseSchool.SCHOOL_LIST_ORDERED);
    }

    public void getSchools() {
        attendantBO = AttendantBO.getInstance();
        if(attendantBO.getUserUid() != null){
            dbRefSchools.child(attendantBO.getUserUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot schoolDS) {
                    List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS = new ArrayList<>();
                    for (DataSnapshot school : schoolDS.getChildren()){
                        final SchoolOrderedByRefPositionDocJson schoolOrderedByRefPositionDocJson
                                = school.getValue(SchoolOrderedByRefPositionDocJson.class);
                        SchoolOrderedByRefPositionBO schoolOrderedByRefPositionBO = new SchoolOrderedByRefPositionBO();
                        schoolOrderedByRefPositionBO.setValues(schoolOrderedByRefPositionDocJson);
                        schoolOrderedByRefPositionBOS.add(schoolOrderedByRefPositionBO);
                    }

                    if(!schoolOrderedByRefPositionBOS.isEmpty()){
                        notifySchoolOrderedChanges(schoolOrderedByRefPositionBOS, true);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    private void notifySchoolOrderedChanges(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS,
                                            boolean isChanged) {
        if(mMainPresenter != null){
            mMainPresenter.getSchoolsListOrdered(schoolOrderedByRefPositionBOS, isChanged);
        }
    }

    public void saveSchools(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS) {
        attendantBO = AttendantBO.getInstance();
        if(attendantBO.getUserUid() != null){
            dbRefSchools = mFirebaseDB.getReference(ConstantsFirebaseSchool.SCHOOL_LIST_ORDERED);

            dbRefSchools.child(attendantBO.getUserUid()).setValue(schoolOrderedByRefPositionBOS)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            }
                        }
                    });
        }
    }


}