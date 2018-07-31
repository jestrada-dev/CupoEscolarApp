package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface ILoginContract {

    interface ILoginView extends IBaseContract.IBaseView{
        void showErrorValidateEditText(EditText editText, String etName);
        void showNeutralDialog(String title, String message, String textNeutralButton);
        void showVerifyEmailDialog(String title, String message, String textPositiveButton,
                                   String textNegativeButton);

        void goToMain();
        void enableFields(boolean enable);

    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter{
        void signInEmailPassword(final EditText etEmail, final EditText etPassword);
        void signInGoogleCredentials();
        void signInFacebookCredentials();
        void forgetMyPassword(final EditText etEmail);

        void getUser(UserBO userBO);

        void onStart();
        void onStop();
        void onDestroy();
    }

}
