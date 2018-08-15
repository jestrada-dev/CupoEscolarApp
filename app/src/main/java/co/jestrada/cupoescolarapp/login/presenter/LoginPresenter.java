package co.jestrada.cupoescolarapp.login.presenter;

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
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class LoginPresenter extends BasePresenter implements
        ILoginContract.ILoginPresenter {

    private Context mContext;
    private ILoginContract.ILoginView mLoginView;
    private UserInteractor mUserInteractor;
    private AttendantInteractor mAttendantInteractor;
    private FirebaseAuth mFirebaseAuth;

    private UserBO userBOApp;

    public LoginPresenter(final Context mContext) {
        this.mContext = mContext;
        this.mLoginView = (ILoginContract.ILoginView) mContext;
        this.mUserInteractor = new UserInteractor(
                this,
                null,
                null);
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                this,
                null,
                null,
                null,
                null
        );
        this.mFirebaseAuth = FirebaseAuth.getInstance();

        userBOApp = UserBO.getInstance();
        if (userBOApp.isOnSession()){
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
                                    userBOApp = UserBO.getInstance();
                                    userBOApp.setuId(mFirebaseUser.getUid());
                                    validatedUserState();
                                    login();
                                } else {
                                    mLoginView.showProgressBar(false);
                                    mLoginView.enableFields(true);
                                    mLoginView.showVerifyEmailDialog(email,
                                            mContext.getString(R.string.firebase_user_already_registered_es),
                                            mContext.getString(R.string.resend_verify_email),
                                            mContext.getString(R.string.check_my_email));
                                }
                            }
                        }else {
                            // TODO: pendiente validar motivos de log in no exitoso.
                            mLoginView.showProgressBar(false);
                            mLoginView.enableFields(true);
                            mLoginView.showNeutralDialog(email,
                                    mContext.getString(R.string.email_password_incorrects),
                                    mContext.getString(R.string.try_again));
                        }
                    }
                });
    }

    private void login() {
        userBOApp = UserBO.getInstance();
        userBOApp.setOnSession(true);
        mLoginView.goToMain();
    }

    private void validatedUserState() {
        userBOApp = UserBO.getInstance();
        if ( (userBOApp.getState() != null) &&
                (userBOApp.getState().toString().equals(StateUserEnum.NOT_VERIFY_EMAIL.name())) ){
            mUserInteractor.activateUser();
            saveAttendant();
        }
    }

    private void saveAttendant() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            AttendantBO attendantBO = new AttendantBO();
            attendantBO.setUserUid(mFirebaseUser.getUid());
            attendantBO.setEmail(mFirebaseUser.getEmail());
            mAttendantInteractor.saveAttendant(attendantBO);
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
                                mLoginView.showNeutralDialog(mFirebaseUser.getEmail(),
                                        mContext.getString(R.string.sent_verify_email),
                                        mContext.getString(R.string.check_my_email));
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
                        if (task.isSuccessful()){
                            mLoginView.showNeutralDialog(email,
                                    mContext.getString(R.string.sent_email_restore_password),
                                    mContext.getString(R.string.check_my_email));
                        } else {
                            mLoginView.showNeutralDialog(email,
                                    mContext.getString(R.string.failed_send_password_reset_email),
                                    mContext.getString(R.string.verify_my_email));

                        }
                    }
                });
    }

}
