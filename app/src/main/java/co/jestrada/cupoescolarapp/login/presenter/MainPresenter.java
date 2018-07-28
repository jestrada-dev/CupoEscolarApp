package co.jestrada.cupoescolarapp.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.contract.IMainContract;

public class MainPresenter extends BasePresenter implements
        IMainContract.IMainPresenter{

    private IMainContract.IMainView mMainView;
    private Context mContext;

    //private LoginInteractor mLoginInteractor;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                if (!firebaseUser.isEmailVerified()){
                    mMainView.goToLogin();
                }
            } else {
                mMainView.goToLogin();
            }
        }
    };

    public MainPresenter(final Context mContext) {
        this.mMainView = (IMainContract.IMainView) mContext;
        //this.mLoginInteractor = new LoginInteractor(this);
        this.mContext = mContext;

        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void signOut() {
        mFirebaseAuth.signOut();
    }

    @Override
    public void onStart() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onDestroy() {
        mAuthListener = null;
    }
}
