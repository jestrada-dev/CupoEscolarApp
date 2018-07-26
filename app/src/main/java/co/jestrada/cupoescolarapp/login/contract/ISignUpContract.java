package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface ISignUpContract extends IBaseContract {

    interface ISignUpView extends IBaseView {

        void showErrorValidateEditText(EditText editText, String etName);
        void showNeutralDialog(String title, String message, String textNeutralButton);
        void showUserCreatedDialog(String title, String message, String textPositiveButton);
        void showResendEmailDialog(String title, String message, String textPositiveButton,
                        String textNegativeButton);
        void goToMain();
        void goToLogin();
        void enableFields(boolean enable);

    }

    interface ISignUpPresenter extends IBasePresenter {

        void signUpEmailPassword(final EditText etEmail, final EditText etPassword);
        void sendVerificationEmail(String email);

    }

}
