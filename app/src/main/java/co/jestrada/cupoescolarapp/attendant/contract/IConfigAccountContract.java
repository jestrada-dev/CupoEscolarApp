package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface IConfigAccountContract {

    interface IConfigAccountView extends IBaseContract.IBaseView{

        void getUserTransactionState(boolean successful);

        void setUserUI(boolean isChanged);

        void validateCredentials(boolean isValidCredentials, String emailOrPassword);
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
