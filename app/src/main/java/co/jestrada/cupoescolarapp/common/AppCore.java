package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.login.model.User;

public class AppCore extends Application {

    private FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified()){
                    User userApp = User.getInstance();
                    goToMain();
                } else {

                }
            } else {
                goToLogin();
            }
        }
    };

    private void goToLogin() {
        Intent intent = new Intent();
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
