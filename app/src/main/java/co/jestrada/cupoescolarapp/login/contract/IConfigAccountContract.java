package co.jestrada.cupoescolarapp.login.contract;

import com.google.firebase.auth.AuthCredential;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface IConfigAccountContract {

    interface IConfigAccountView extends IBaseContract.IBaseView{

        void getUserTransactionState(boolean successful);

        void setUserUI(UserBO userBO, boolean isChanged);

        void validateCredentials(boolean isValidCredentials);
        void changeEmail(boolean isSuccessful);
        void changePassword(boolean isSuccessful);
        void showDialogSendVerifyEmail();
    }

    interface IConfigAccountPresenter extends IBaseContract.IBasePresenter{

        void getUserTransactionState(boolean successful);

        void getData();

        void validateCredentials(String password, String emailOrPassword);
        void changeEmailAccount(String newEmail);
        void changePassword(String newPassword);

    }

}
