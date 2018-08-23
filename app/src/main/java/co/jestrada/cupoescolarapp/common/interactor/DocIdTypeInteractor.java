package co.jestrada.cupoescolarapp.common.interactor;

import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.common.constant.ConstantsFirebaseDocIdType;
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.modelDocJson.DocIdTypeDocJson;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;

public class DocIdTypeInteractor implements
        IBaseContract.IBaseInteractor{

    private IEditProfileContract.IEditProfilePresenter mEditProfilePresenter;
    private IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefDocIdTypes;

    public DocIdTypeInteractor(@Nullable IEditProfileContract.IEditProfilePresenter mEditProfilePresenter,
                               @Nullable IAddEditStudentContract.IAddEditStudentPresenter mAddEditStudentPresenter) {
        this.mEditProfilePresenter = mEditProfilePresenter;
        this.mAddEditStudentPresenter = mAddEditStudentPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefDocIdTypes = mFirebaseDB.getReference(ConstantsFirebaseDocIdType.DOC_ID_TYPES);
    }

    public void getDocIdTypes() {
        dbRefDocIdTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot docIdTypesDS) {
                ArrayList<DocIdTypeBO> docIdTypeBOS = new ArrayList<>();
                for( DataSnapshot docIdTypesChildDS : docIdTypesDS.getChildren()){
                    DocIdTypeDocJson docIdTypeDocJson = docIdTypesChildDS.getValue(DocIdTypeDocJson.class);
                    DocIdTypeBO docIdTypeBO = new DocIdTypeBO();
                    docIdTypeBO.setValues(docIdTypeDocJson);
                    docIdTypeBOS.add(docIdTypeBO);
                }
                notifyAttandantChanges(docIdTypeBOS, true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyAttandantChanges(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged) {
        if(mEditProfilePresenter != null){
            mEditProfilePresenter.getDocIdTypes(docIdTypeBOS, isChanged);
        }
        if(mAddEditStudentPresenter != null){
            mAddEditStudentPresenter.getDocIdTypes(docIdTypeBOS, isChanged);
        }
    }

}

