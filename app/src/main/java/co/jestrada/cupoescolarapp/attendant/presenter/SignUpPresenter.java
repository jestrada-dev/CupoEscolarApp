package co.jestrada.cupoescolarapp.attendant.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import co.jestrada.cupoescolarapp.attendant.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.attendant.model.enums.StateUserEnum;

public class SignUpPresenter extends BasePresenter implements
        ISignUpContract.ISignUpPresenter{

    private Context mContext;
    private ISignUpContract.ISignUpView mSignUpView;
    private FirebaseAuth mFirebaseAuth;
    private AttendantInteractor mAttendantInteractor;

    private AttendantBO attendantBO;

    public SignUpPresenter(final Context mContext) {
        this.mContext = mContext;
        this.mSignUpView = (ISignUpContract.ISignUpView) mContext;
        this.mFirebaseAuth = FirebaseAuth.getInstance();

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
                mSignUpView.showNotConnectionDialog();
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
                            saveAttendant();
                            sendVerificationEmail(email);
                        } else {
                            if(task.getException().getMessage().equals
                                    (mContext.getString(R.string.firebase_user_already_registered))){
                                mSignUpView.showUserAlreadyRegisteredDialog();
                            } else {
                                mSignUpView.showNotIdentifyErrorOnSignUp();
                            }
                        }
                    }

                });

    }

    private void saveAttendant() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            attendantBO = AttendantBO.getInstance();
            attendantBO.setUserUid(mFirebaseUser.getUid());
            attendantBO.setEmail(mFirebaseUser.getEmail());
            attendantBO.setState(StateUserEnum.INACTIVE);
            mAttendantInteractor.saveAttendant();
        }
    }

    @Override
    public void sendVerificationEmail(String email) {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            mFirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mSignUpView.showVerifyEmailSentDialog(task.isSuccessful());
                }
            });
        }
    }

}
