package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.view.MainActivity;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class AppCore extends Application {

    private UserInteractor mUserInteractor;

    private UserBO userBOApp;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            userBOApp = UserBO.getInstance();
            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified()){
                    if(!userBOApp.isOnSession()){
                        goToMain();
                        userBOApp.setOnSession(true);
                        verifiedUserState();
                    }
                }
            } else {
                userBOApp.setOnSession(false);
                goToLogin();
            }
        }
    };

    public void verifiedUserState() {
/*        userBOApp = UserBO.getInstance();
        if ( (userBOApp.getState() != null) &&
                (userBOApp.getState().toString().equals(StateUserEnum.NOT_VERIFY_EMAIL.name())) ){
            mUserInteractor.activateUser();
        }*/
    }

    private void goToLogin() {
        Intent intent = new Intent(AppCore.this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToMain() {
        Intent intent = new Intent(AppCore.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.addAuthStateListener(mAuthListener);

        mUserInteractor = new UserInteractor();
    }
}
