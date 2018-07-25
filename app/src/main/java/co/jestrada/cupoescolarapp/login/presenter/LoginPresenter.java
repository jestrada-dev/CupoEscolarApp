package co.jestrada.cupoescolarapp.login.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.constant.Fields;
import co.jestrada.cupoescolarapp.common.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;

public class LoginPresenter extends BasePresenter implements
        ILoginContract.ILoginPresenter {

    private ILoginContract.ILoginView mLoginView;
    private Context mContext;

    public LoginPresenter(final Context mContext) {
        this.mLoginView = (ILoginContract.ILoginView) mContext;
        this.mContext = mContext;
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
            mLoginView.enableFields(false);
            mLoginView.goToEnrolledStudents();
        }
    }

    @Override
    public void signInGoogleCredentials() {

    }

    @Override
    public void signInFacebookCredentials() {

    }
}
