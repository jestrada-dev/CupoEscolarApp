package co.jestrada.cupoescolarapp.login.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.LoginMethodBO;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface ISignUpContract extends IBaseContract {

    interface ISignUpView extends IBaseView {

        void showNeutralDialog(String title, String message, String textNeutralButton);
        void showUserCreatedDialog(String title, String message, String textPositiveButton);
        void showUserAlreadyRegisteredDialog(String title, String message, String textPositiveButton,
                        String textNegativeButton);
        void goToMain();
        void goToLogin();
        void enableFields(boolean enable);

    }

    interface ISignUpPresenter extends IBasePresenter {

        void signUpEmailPassword(final String email, final String password);
        void sendVerificationEmail(String email);

    }

    interface IUserInteractor extends IBaseContract.IBaseInteractor{
        void getUser(final String userUid);
        void saveUser(UserBO userBO);
    }

}
