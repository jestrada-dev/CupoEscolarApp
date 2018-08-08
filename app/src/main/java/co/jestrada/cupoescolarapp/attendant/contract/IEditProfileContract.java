package co.jestrada.cupoescolarapp.attendant.contract;

import android.location.Location;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPointBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;

public interface IEditProfileContract extends IBaseContract {

    interface IEditProfileView{
        void goToLogin();
        void setAttendantUI(AttendantBO attendantBO);
        void setDocIdTypesList(ArrayList<DocIdTypeBO> docIdTypeBOS);
        void setCoordsCurrentPositionUI(Location location);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface IEditProfilePresenter{

        void getAttendant(AttendantBO attendantBO);
        void saveAttendant(AttendantBO attendantBO);
        void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS);
        void getCoordsCurrentPosition();

        void signOut();
        void onStart();
        void onStop();
        void onDestroy();
    }

}