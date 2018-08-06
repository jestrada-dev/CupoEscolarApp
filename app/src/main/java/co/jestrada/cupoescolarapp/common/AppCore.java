package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.view.MainActivity;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class AppCore extends Application implements ILoginContract.IAppCore {

    UserBO userBOApp;

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            userBOApp = UserBO.getInstance();
            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified()){
                    if(!userBOApp.isOnSession()){
                        userBOApp.setOnSession(true);
                    }
                }
            } else {
                userBOApp.setOnSession(false);
            }
        }
    };

    private void startLogin() {
        Intent intent = new Intent(AppCore.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startLogin();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.addAuthStateListener(mAuthListener);

    }

}
