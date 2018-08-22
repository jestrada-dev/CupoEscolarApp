package co.jestrada.cupoescolarapp.attendant.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.attendant.contract.ILoginContract;
import co.jestrada.cupoescolarapp.attendant.model.enums.StateUserEnum;

public class LoginPresenter extends BasePresenter implements
        ILoginContract.ILoginPresenter {

    private Context mContext;
    private ILoginContract.ILoginView mLoginView;
    private AttendantInteractor mAttendantInteractor;
    private FirebaseAuth mFirebaseAuth;

    private AttendantBO attendantBO;

    public LoginPresenter(final Context mContext) {
        this.mContext = mContext;
        this.mLoginView = (ILoginContract.ILoginView) mContext;
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                this,
                null,
                null,
                null,
                null
        );
        this.mFirebaseAuth = FirebaseAuth.getInstance();

        attendantBO = AttendantBO.getInstance();
        if (attendantBO.isOnSession()){
            mLoginView.goToMain();
        }
    }

    @Override
    public void signInEmailPassword(final String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener((Activity)mLoginView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            if(mFirebaseUser != null){
                                if(mFirebaseUser.isEmailVerified()){
                                    validatedUserState();
                                    login();
                                } else {
                                    mLoginView.showNotVerifyEmailDialog();
                                }
                            }
                        }else {
                            // TODO: pendiente validar motivos de log in no exitoso.
                            mLoginView.showNotCredentials();
                        }
                    }
                });
    }

    private void login() {
        attendantBO = AttendantBO.getInstance();
        attendantBO.setOnSession(true);
        mLoginView.goToMain();
    }

    private void validatedUserState() {
        attendantBO = AttendantBO.getInstance();
        if(attendantBO.getState() == null || attendantBO.getState().toString().equals(StateUserEnum.INACTIVE.name())){
            mAttendantInteractor.activateUser();
        }
    }

    public void sendVerificationEmail() {
        final FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            mFirebaseUser.sendEmailVerification().addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mLoginView.showVerifyEmailSentDialog();
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void forgetMyPassword(String email) {
        sendRestorePasswordEmail(email);
    }

    private void sendRestorePasswordEmail(final String email) {
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mLoginView.showRestorePasswordEmailSentDialog(task.isSuccessful());
                    }
                });
    }

}
