package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface ILoginContract {

    interface ILoginView extends IBaseContract.IBaseView{
        void showNeutralDialog(String title, String message, String textNeutralButton);
        void showVerifyEmailDialog(String title, String message, String textPositiveButton,
                                   String textNegativeButton);

        void goToMain();
        void enableFields(boolean enable);

    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter{
        void signInEmailPassword(String email, String password);
        void forgetMyPassword(String email);

    }

    interface IUserInteractor extends IBaseContract.IBaseInteractor{
        void getUser(final String userUid);
        void saveUser(UserBO userBO);
        void activateUser();
    }

}
