package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.login.interactor.LoginInteractor;
import co.jestrada.cupoescolarapp.login.model.User;
import co.jestrada.cupoescolarapp.login.presenter.LoginPresenter;
import co.jestrada.cupoescolarapp.login.presenter.SignUpPresenter;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class AppCore extends Application {

    private FirebaseAuth mFirebaseAuth;
    private SignUpPresenter mSignUpPresenter;
    private LoginPresenter mLoginPresenter;

    private LoginInteractor loginInteractor;

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified()){
                    loginInteractor.getUser(firebaseUser.getUid());
                    User userApp = User.getInstance();
                    userApp.setOnSession(true);
                    //goToMain();
                }
            } else {
                User userApp = User.getInstance();
                userApp.setOnSession(false);
                //goToLogin();
            }
        }
    };

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToMain() {
        Intent intent = new Intent();
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAuth = FirebaseAuth.getInstance();
        initAuthListener();

        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopAuthListener();
    }

    private void initAuthListener() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void stopAuthListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

}
