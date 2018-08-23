package co.jestrada.cupoescolarapp.common.interactor;

import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.constant.ConstantsFirebaseRelationshipType;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.common.model.modelDocJson.RelationshipTypeDocJson;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;

public class RelationshipTypeInteractor implements
        IBaseContract.IBaseInteractor{

    private IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefRelationshipTypes;

    public RelationshipTypeInteractor(@Nullable IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter) {
        this.mAddEditStudentPresenter = mAddEditStudentPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefRelationshipTypes = mFirebaseDB.getReference(ConstantsFirebaseRelationshipType.RELATIONSHIP_TYPE);
    }

    public void getRelationshipTypes() {
        dbRefRelationshipTypes.child(ConstantsFirebaseRelationshipType.RELATIONSHIP_TYPE_SPANISH)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot relationshipTypesDS) {
                ArrayList<RelationshipTypeBO> relationshipTypeBOS = new ArrayList<>();
                for( DataSnapshot relationshipTypesChildDS : relationshipTypesDS.child(ConstantsFirebaseRelationshipType.RELATIONSHIP_TYPE_SPANISH).getChildren()){
                    RelationshipTypeDocJson relationshipTypeDocJson = relationshipTypesChildDS.
                            getValue(RelationshipTypeDocJson.class);
                    RelationshipTypeBO relationshipTypeBO = new RelationshipTypeBO();
                    relationshipTypeBO.setValues(relationshipTypeDocJson);
                    relationshipTypeBOS.add(relationshipTypeBO);
                }
                notifyRelationshipTypesChanges(relationshipTypeBOS, true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyRelationshipTypesChanges(ArrayList<RelationshipTypeBO> relationshipTypeBOS, boolean isChanged) {
        if(mAddEditStudentPresenter != null){
            mAddEditStudentPresenter.getRelationshipTypes(relationshipTypeBOS, isChanged);
        }
    }

}

