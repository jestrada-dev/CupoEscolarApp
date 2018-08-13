package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface IMainContract extends IBaseContract {

    interface IMainView{
        void goToLogin();
        void setNavViewUI(AttendantBO attendantBO, boolean isChanged);
    }

    interface IMainPresenter{
        void signOut();

        void getUser(UserBO userBO);
        void getAttendant(AttendantBO attendantBO, boolean isChanged);
        void getRefPosition(RefPositionBO refPositionBO, boolean isChanged);

        void onStart();
        void onStop();
        void onDestroy();
    }

}
