package co.jestrada.cupoescolarapp.attendant.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.common.interactor.DocIdTypeInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;

public class EditProfilePresenter extends BasePresenter implements
        IEditProfileContract.IEditProfilePresenter,
        GoogleApiClient.OnConnectionFailedListener {

    private IEditProfileContract.IEditProfileView mEditProfileView;
    private Context mContext;

    private DocIdTypeInteractor mDocIdTypeInteractor;
    private AttendantInteractor mAttendantInteractor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private AttendantBO attendantBO;

    public EditProfilePresenter(final Context mContext) {
        this.mEditProfileView = (IEditProfileContract.IEditProfileView) mContext;
        this.mDocIdTypeInteractor = new DocIdTypeInteractor(this
        , null);
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                null,
                null,
                this,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();
        attendantBO = AttendantBO.getInstance();
    }

    @Override
    public void getAttendantTransactionState(boolean successful) {
        mEditProfileView.getAttendantTransactionState(successful);
    }

    @Override
    public void getData() {
        mDocIdTypeInteractor.getDocIdTypes();
        mAttendantInteractor.getAttendant();
    }

    @Override
    public void getAttendant(boolean isChanged) {
        mEditProfileView.setAttendantUI(isChanged);
    }

    @Override
    public void saveAttendant() {
        mAttendantInteractor.saveAttendant();
    }

    @Override
    public void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged) {
        if (!docIdTypeBOS.isEmpty()){
            mEditProfileView.setDocIdTypesList(docIdTypeBOS, isChanged);
        }
    }


    @Override
    public void signOut() {
        attendantBO = AttendantBO.getInstance();
        attendantBO.setOnSession(false);
        mFirebaseAuth.signOut();
        mEditProfileView.goToLogin();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
