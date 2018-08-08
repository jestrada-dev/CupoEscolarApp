package co.jestrada.cupoescolarapp.attendant.interactor;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.DocIdTypeDocJson;
import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public class DocIdTypeInteractor implements
        IBaseContract.IBaseInteractor{

    private IEditProfileContract.IEditProfilePresenter mEditProfilePresenter;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference dbRefDocIdTypes;

    public DocIdTypeInteractor(@Nullable IEditProfileContract.IEditProfilePresenter mEditProfilePresenter) {
        this.mEditProfilePresenter = mEditProfilePresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
        this.dbRefDocIdTypes = mFirebaseDB.getReference(Firebase.DOC_ID_TYPES);
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
                Log.d("DocIdType","DocIdTypeInteractor -> Se ejecutó el onDataChange para " + Firebase.DOC_ID_TYPES);
                notifyAttandantChanges(docIdTypeBOS);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void notifyAttandantChanges(ArrayList<DocIdTypeBO> docIdTypeBOS) {
        if(mEditProfilePresenter != null){
            mEditProfilePresenter.getDocIdTypes(docIdTypeBOS);
        }
    }

}

