package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface ILoginContract {

    interface ILoginView extends IBaseContract.IBaseView{
        void showErrorValidateEditText(EditText editText, String etName);
        void loginSuccess();
        void loginError();
        void enableFields(boolean enable);

        void goToEnrolledStudents();
    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter{
        void signInEmailPassword(final EditText etEmail, final EditText etPassword);
        void signInGoogleCredentials();
        void signInFacebookCredentials();
    }

}
