package co.jestrada.cupoescolarapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.constant.Fields;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;

public class LoginPresenter extends BasePresenter implements
        ILoginContract.ILoginPresenter {

    private ILoginContract.ILoginView mLoginView;
    private Context mContext;

    private UserInteractor mUserInteractor;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                mUserInteractor.getUser(firebaseUser.getUid());
                UserBO userBOApp = UserBO.getInstance();
                if (firebaseUser.isEmailVerified()){
                    if (userBOApp.getState().equals(StateUserEnum.NOT_VERIFY_EMAIL.name())){
                        userBOApp.setState(StateUserEnum.ACTIVE);
                        LoginMethodBO loginMethodBO = new LoginMethodBO();
                        //TODO: Corregir el manejo de la fecha.
                        loginMethodBO.setCreationTimestamp("");
                        loginMethodBO.setLoginMethod(LoginMethodEnum.EMAIL_AND_PASSWORD);
                        loginMethodBO.setEmail(firebaseUser.getEmail());
                        loginMethodBO.setState(StateUserEnum.ACTIVE);
                        mUserInteractor.saveUser( userBOApp, loginMethodBO);
                    }
                    mLoginView.goToMain();
                }
            }
        }
    };


    public LoginPresenter(final Context mContext) {
        this.mLoginView = (ILoginContract.ILoginView) mContext;
        this.mContext = mContext;

        mUserInteractor = new UserInteractor(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8;
    }

    private boolean isValidCredentials(EditText etEmail, EditText etPassword){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        boolean validCredentials = true;
        if(!isValidPassword(password)){
            mLoginView.showErrorValidateEditText(etPassword, Fields.PASSWORD);
            etPassword.requestFocus();
            validCredentials = false;
        }
        if(!isValidEmail(email)){
            mLoginView.showErrorValidateEditText(etEmail, Fields.EMAIL);
            etEmail.requestFocus();
            validCredentials = false;
        }
        return validCredentials;
    }

    @Override
    public void signInEmailPassword(EditText etEmail, EditText etPassword) {
        if (isValidCredentials(etEmail, etPassword)){
            final String email = etEmail.getText().toString().trim().toLowerCase();
            String password = etPassword.getText().toString();

            mLoginView.enableFields(false);
            mLoginView.showProgressBar(true);

            mFirebaseAuth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener((Activity)mLoginView, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mLoginView.showProgressBar(false);
                    if (task.isSuccessful()){
                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        if(mFirebaseUser.isEmailVerified()){
                            mLoginView.goToMain();
                        } else {
                            mLoginView.showVerifyEmailDialog(email,
                                    mContext.getString(R.string.firebase_user_already_registered_es),
                                    mContext.getString(R.string.resend_verify_email),
                                    mContext.getString(R.string.check_my_email));
                        }
                    }else {
                        mLoginView.enableFields(true);
                        mLoginView.showNeutralDialog(email,
                                mContext.getString(R.string.email_password_incorrects),
                                mContext.getString(R.string.try_again));
                    }
                }
            });
        }
    }

    @Override
    public void signInGoogleCredentials() {

    }

    @Override
    public void signInFacebookCredentials() {

    }

    @Override
    public void forgetMyPassword(EditText etEmail) {
        String email = etEmail.getText().toString().trim();
        if(!isValidEmail(email)){
            mLoginView.showErrorValidateEditText(etEmail, Fields.FORGET_EMAIL);
            etEmail.requestFocus();
        }else {
            sendRestorePasswordEmail(email);
        }
    }

    @Override
    public void getUser(UserBO userBO) {

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
