package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.view.MainActivity;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class AppCore extends Application implements IAppCoreContract.IAppCore {

    UserBO userBOApp;

    private AttendantInteractor mAttendantInteractor;

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            Log.d("AuthListener","AppCore -> Se ejecutó el Listener.");
            userBOApp = UserBO.getInstance();
            if (firebaseUser != null) {
                Log.d("AuthListener","AppCore -> Usuario conectado: " + firebaseUser.getEmail());
                if (firebaseUser.isEmailVerified()){
                    if(!userBOApp.isOnSession()){
                        userBOApp.setOnSession(true);
                        mAttendantInteractor.getAttendant(firebaseUser.getUid());
                    }
                }
            } else {
                Log.d("AppCore","AppCore -> Usuario desconectado");
                userBOApp.setOnSession(false);
            }
        }
    };

    private void startLogin() {
        Intent intent = new Intent(AppCore.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.d("LoginActivity","AppCore -> Iniciando Login Activity");
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startLogin();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.addAuthStateListener(mAuthListener);

        mAttendantInteractor = new AttendantInteractor(
                this,
                null,
                null,
                null,
                null
                );

    }

    @Override
    public void getAttendant(AttendantBO attendantBO) {
        userBOApp.setValues(attendantBO);
    }
}
