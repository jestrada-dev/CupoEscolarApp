package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface ILoginContract {

    interface ILoginView extends IBaseContract.IBaseView{
        void showErrorValidateEditText(EditText editText, String etName);
        void showNeutralDialog(String title, String message, String textNeutralButton);
        void goToMain();
        void enableFields(boolean enable);

    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter{
        void signInEmailPassword(final EditText etEmail, final EditText etPassword);
        void signInGoogleCredentials();
        void signInFacebookCredentials();
        void forgetMyPassword(final EditText etEmail);

        void onStart();
        void onStop();
        void onDestroy();
    }

    interface ILoginInteractor extends IBaseContract.IBaseInteractor{
        
    }

}
