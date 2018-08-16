package co.jestrada.cupoescolarapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.common.constant.CustomDateUtils;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.enums.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public class SignUpPresenter extends BasePresenter implements
        ISignUpContract.ISignUpPresenter{

    private Context mContext;
    private ISignUpContract.ISignUpView mSignUpView;
    private FirebaseAuth mFirebaseAuth;
    private UserInteractor mUserInteractor;
    private AttendantInteractor mAttendantInteractor;

    private UserBO userBOApp;

    public SignUpPresenter(final Context mContext) {
        this.mContext = mContext;
        this.mSignUpView = (ISignUpContract.ISignUpView) mContext;
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.mUserInteractor = new UserInteractor(
                null,
                this,
                null);
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                this,
                null,
                null,
                null
        );
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
    public void signUpEmailPassword(String email, String password) {
            if(!isInternetConnectionActive()){
                mSignUpView.enableFields(true);
                mSignUpView.showProgressBar(false);
                mSignUpView.showNeutralDialog(mContext.getString(R.string.create_account),
                        mContext.getString(R.string.errorOnInternetConnection),
                        mContext.getString(R.string.try_later));
            } else{
                createUserEmailPassword(email, password);
            }
    }

    private void createUserEmailPassword(final String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUser();
                            sendVerificationEmail(email);
                        } else {
                            mSignUpView.enableFields(true);
                            mSignUpView.showProgressBar(false);
                            if(task.getException().getMessage().equals
                                    (mContext.getString(R.string.firebase_user_already_registered))){
                                mSignUpView.showUserAlreadyRegisteredDialog(email,
                                        mContext.getString(R.string.firebase_user_already_registered_es),
                                        mContext.getString(R.string.login),
                                        mContext.getString(R.string.try_another_email_account));
                            } else {
                                mSignUpView.showNeutralDialog(email,
                                        mContext.getString(R.string.sign_up_user_failed),
                                        mContext.getString(R.string.try_later));
                            }
                        }
                    }

                });

    }

    private void saveUser() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            userBOApp = UserBO.getInstance();
            userBOApp.setuId(mFirebaseUser.getUid());
            userBOApp.setEmail(mFirebaseUser.getEmail());
            userBOApp.setState(StateUserEnum.NOT_VERIFY_EMAIL);
            ArrayList<LoginMethodBO> loginMethodBOS = new ArrayList<>();
            LoginMethodBO loginMethodBO = new LoginMethodBO();
            long longFecha = mFirebaseUser.getMetadata().getCreationTimestamp();
            String strFecha = DateFormat.format(CustomDateUtils.LONG_DATE, new Date(longFecha)).toString();
            loginMethodBO.setCreationTimestamp(strFecha);
            loginMethodBO.setActivateTimestamp("");
            loginMethodBO.setLoginMethod(LoginMethodEnum.EMAIL_AND_PASSWORD);
            loginMethodBO.setEmail(mFirebaseUser.getEmail());
            loginMethodBO.setState(StateUserEnum.NOT_VERIFY_EMAIL);
            loginMethodBOS.add(loginMethodBO);

            userBOApp.setLogins(loginMethodBOS);
            mUserInteractor.saveUser(userBOApp);
        }
    }

    @Override
    public void sendVerificationEmail(String email) {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            mFirebaseUser.sendEmailVerification();
            mSignUpView.showProgressBar(false);
            mSignUpView.showUserCreatedDialog(email,
                    mContext.getString(R.string.sign_up_user_succesfully),
                    mContext.getString(R.string.check_my_email));
        }
    }

}
