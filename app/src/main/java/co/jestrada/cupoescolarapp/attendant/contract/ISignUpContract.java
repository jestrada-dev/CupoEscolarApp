package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface ISignUpContract extends IBaseContract {

    interface ISignUpView extends IBaseView {

        void showNotConnectionDialog();
        void showVerifyEmailSentDialog(boolean isSuccessful);
        void showNotIdentifyErrorOnSignUp();
        void showUserAlreadyRegisteredDialog();
        void goToMain();
        void goToLogin();
        void enableFields(boolean enable);

    }

    interface ISignUpPresenter extends IBasePresenter {

        void signUpEmailPassword(final String email, final String password);
        void sendVerificationEmail(String email);

    }

}
