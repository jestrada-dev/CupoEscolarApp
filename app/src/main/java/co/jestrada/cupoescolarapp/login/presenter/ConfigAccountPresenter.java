package co.jestrada.cupoescolarapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.constants.ConstantsAccount;
import co.jestrada.cupoescolarapp.login.contract.IConfigAccountContract;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.interactor.UserInteractor;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.model.enums.StateUserEnum;

public class ConfigAccountPresenter extends BasePresenter implements
        IConfigAccountContract.IConfigAccountPresenter {

    private Context mContext;
    private IConfigAccountContract.IConfigAccountView mConfigAccountView;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private UserBO userBOApp;

    public ConfigAccountPresenter(final Context mContext) {
        this.mContext = mContext;
        this.mConfigAccountView = (IConfigAccountContract.IConfigAccountView) mContext;

        this.mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        userBOApp = UserBO.getInstance();
    }


    public void sendVerificationEmail() {
        if (mFirebaseUser != null){
            mFirebaseUser.sendEmailVerification().addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mConfigAccountView.showDialogSendVerifyEmail();
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void getUserTransactionState(boolean successful) {

    }

    @Override
    public void getData() {
        mConfigAccountView.setUserUI(userBOApp,true);
    }

    @Override
    public void validateCredentials(final String password, final String emailOrPassword) {
        if (mFirebaseUser != null){
            AuthCredential authCredential = EmailAuthProvider.getCredential(mFirebaseUser.getEmail(), password);
            mFirebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(emailOrPassword.equals(ConstantsAccount.CHANGE_EMAIL)){
                        mConfigAccountView.validateCredentials(task.isSuccessful());
                    } else {
                        if(task.isSuccessful()){
                            changePassword(password);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void changeEmailAccount(final String newEmail) {
        if (mFirebaseUser != null){
            mFirebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendVerificationEmail();
                    }
                }
            });
        }
    }

    @Override
    public void changePassword(String newPassword) {
        if (mFirebaseUser != null){
            mFirebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mConfigAccountView.changePassword(task.isSuccessful());
                }
            });
        }
    }
}
