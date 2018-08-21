package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.common.contract.IAppCoreContract;

public class AppCore extends Application implements IAppCoreContract.IAppCore{

    AttendantBO attendantBO;

    private AttendantInteractor mAttendantInteractor;

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            attendantBO = AttendantBO.getInstance();
            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified()){
                    if(!attendantBO.isOnSession()){
                        attendantBO.setOnSession(true);
                        mAttendantInteractor.getAttendant(firebaseUser.getUid());
                    }
                }
            } else {
                attendantBO.setOnSession(false);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.addAuthStateListener(mAuthListener);

        mAttendantInteractor = new AttendantInteractor(
                this,
                null,
                null,
                null,
                null,
                null);
    }


}
