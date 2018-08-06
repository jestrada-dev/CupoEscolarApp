package co.jestrada.cupoescolarapp.attendant.contract;

import android.location.Location;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPointBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface IAttendantProfileContract extends IBaseContract {

    interface IAttendantProfileView{
        void goToLogin();
        void setAttendantUI(AttendantBO attendantBO);
        void setCoordsCurrentPositionUI(Location location);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface IAttendantProfilePresenter{

        void getAttendant(AttendantBO attendantBO);
        void saveAttendant(AttendantBO attendantBO);
        void getCoordsCurrentPosition();

        void signOut();
        void onStart();
        void onStop();
        void onDestroy();
    }

}
