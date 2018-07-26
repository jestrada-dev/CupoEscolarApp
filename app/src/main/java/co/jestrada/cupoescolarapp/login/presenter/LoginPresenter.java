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

public class LoginPresenter extends BasePresenter implements
        ILoginContract.ILoginPresenter {

    private ILoginContract.ILoginView mLoginView;
    private Context mContext;

    private FirebaseAuth mFirebaseAuth;

    public LoginPresenter(final Context mContext) {
        this.mLoginView = (ILoginContract.ILoginView) mContext;
        this.mContext = mContext;

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
                        mLoginView.goToMain();
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
            sendVerificationEmail(email);
        }
    }

    private void sendVerificationEmail(final String email) {
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
