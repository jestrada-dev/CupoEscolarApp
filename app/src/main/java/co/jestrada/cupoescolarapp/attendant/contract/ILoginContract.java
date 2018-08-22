package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface ILoginContract {

    interface ILoginView extends IBaseContract.IBaseView{
        void showNotVerifyEmailDialog();
        void showNotCredentials();
        void showVerifyEmailSentDialog();
        void showRestorePasswordEmailSentDialog(boolean isSuccessful);

        void goToMain();

    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter{
        void signInEmailPassword(String email, String password);
        void forgetMyPassword(String email);

    }

}
