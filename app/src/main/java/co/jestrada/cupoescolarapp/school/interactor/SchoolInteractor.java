package co.jestrada.cupoescolarapp.school.interactor;

import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.school.constant.ConstantsFirebaseSchool;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolDocJson;

public class SchoolInteractor implements IBaseContract.IBaseInteractor{

    private IMainContract.IMainPresenter mMainPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefSchools;

    public SchoolInteractor(@Nullable IMainContract.IMainPresenter mMainPresenter) {
        this.mMainPresenter = mMainPresenter;

        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefSchools = mFirebaseDB.getReference(ConstantsFirebaseSchool.SCHOOL);
    }

    public void getSchools() {
        dbRefSchools.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot schoolDS) {
                    final SchoolDocJson schoolDocJson = schoolDS.getValue(SchoolDocJson.class);
                    if(schoolDocJson != null){
                        SchoolBO schoolBO = new SchoolBO();
                        schoolBO.setValues(schoolDocJson);
                        notifySchoolChanges(schoolBO, true);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    private void notifySchoolChanges(SchoolBO schoolBO, boolean isChanged) {
        if(mMainPresenter != null){
            mMainPresenter.getSchools(schoolBO, isChanged);
        }
    }

}