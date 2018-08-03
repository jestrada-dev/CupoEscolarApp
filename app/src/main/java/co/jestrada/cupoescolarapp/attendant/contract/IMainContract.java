package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface IMainContract extends IBaseContract {

    interface IMainView{
        void goToLogin();
        void setNavViewUI(AttendantBO attendantBO);
    }

    interface IMainPresenter{
        void signOut();

        void getUser(UserBO userBO);
        void getAttendant(AttendantBO attendantBO);

        void onStart();
        void onStop();
        void onDestroy();
    }

}
