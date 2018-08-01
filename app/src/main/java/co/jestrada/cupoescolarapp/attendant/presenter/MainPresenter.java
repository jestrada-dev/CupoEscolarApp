package co.jestrada.cupoescolarapp.attendant.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class MainPresenter extends BasePresenter implements
        IMainContract.IMainPresenter{

    private IMainContract.IMainView mMainView;
    private Context mContext;

    private UserInteractor mUserInteractor;

    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public MainPresenter(final Context mContext) {
        this.mMainView = (IMainContract.IMainView) mContext;
        this.mUserInteractor = new UserInteractor(null, null, this);
        this.mContext = mContext;
    }


    private void getData(){

    }

    @Override
    public void signOut() {
        mFirebaseAuth.signOut();
        userBOApp = UserBO.getInstance();
        userBOApp.setOnSession(false);
        mMainView.goToLogin();
    }

    @Override
    public void getUser(UserBO userBO) {

    }

    @Override
    public void onStart() {
        userBOApp = UserBO.getInstance();
        if (!userBOApp.isOnSession()){
            signOut();
        } else {
            getData();
        }
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
        
    }
}
