package co.jestrada.cupoescolarapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import co.jestrada.cupoescolarapp.common.AppCore;
import co.jestrada.cupoescolarapp.common.constant.Fields;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.interactor.LoginInteractor;
import co.jestrada.cupoescolarapp.login.model.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.User;
import co.jestrada.cupoescolarapp.login.model.UserModel;

public class SignUpPresenter extends BasePresenter
        implements ISignUpContract.ISignUpPresenter {

    private ISignUpContract.ISignUpView mSignUpView;
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;

    public SignUpPresenter(final Context mContext) {
        mSignUpView = (ISignUpContract.ISignUpView) mContext;
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
            mSignUpView.showErrorValidateEditText(etPassword, Fields.PASSWORD);
            etPassword.requestFocus();
            validCredentials = false;
        }
        if(!isValidEmail(email)){
            mSignUpView.showErrorValidateEditText(etEmail, Fields.EMAIL);
            etEmail.requestFocus();
            validCredentials = false;
        }
        return validCredentials;
    }

    private boolean isInternetConnectionActive(){
        return isNetAvailable() && isOnlineNet();
    }

    private boolean isNetAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return (mNetworkInfo != null && mNetworkInfo.isConnected());
    }

    private boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void signUpEmailPassword(EditText etEmail, EditText etPassword) {
        if (isValidCredentials(etEmail, etPassword)){
            if(!isInternetConnectionActive()){
                mSignUpView.enableFields(false);
                mSignUpView.showProgressBar(false);
                mSignUpView.showNeutralDialog(mContext.getString(R.string.create_account),
                        mContext.getString(R.string.errorOnInternetConnection),
                        mContext.getString(R.string.try_later));
            } else{
                final String email = etEmail.getText().toString().trim().toLowerCase();
                String password = etPassword.getText().toString();
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity)mContext, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mSignUpView.showProgressBar(false);
                                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                if (task.isSuccessful()) {
                                    sendVerificationEmail(email);
                                } else {
                                    if(task.getException().getMessage().equals
                                            (mContext.getString(R.string.firebase_user_already_registered))){
                                        if (mFirebaseUser.isEmailVerified()){
                                            mSignUpView.goToMain();
                                        } else {
                                            mSignUpView.showResendEmailDialog(email,
                                                    mContext.getString(R.string.firebase_user_already_registered_es),
                                                    mContext.getString(R.string.resend_verification_email),
                                                    mContext.getString(R.string.got_email));
                                        }
                                    } else {
                                        mSignUpView.showNeutralDialog(email,
                                                mContext.getString(R.string.sign_up_user_failed),
                                                mContext.getString(R.string.try_later));
                                        mSignUpView.enableFields(true);
                                    }
                                }
                            }

                        });
            }
        }

    }

    @Override
    public void sendVerificationEmail(String email) {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            mFirebaseUser.sendEmailVerification();
            mSignUpView.showUserCreatedDialog(email,
                    mContext.getString(R.string.sign_up_user_succesfully),
                    mContext.getString(R.string.check_my_email));
        }else{
            mSignUpView.showNeutralDialog(email, mContext.getString(R.string.failed_resend_verification_email),
                    mContext.getString(R.string.change_email_account));
        }
    }

}
