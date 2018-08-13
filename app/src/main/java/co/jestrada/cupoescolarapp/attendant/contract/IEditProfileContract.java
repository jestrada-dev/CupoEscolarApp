package co.jestrada.cupoescolarapp.attendant.contract;

import android.location.Location;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface IEditProfileContract extends IBaseContract {

    interface IEditProfileView{
        void goToLogin();
        void setAttendantUI(AttendantBO attendantBO, boolean isChanged);
        void setDocIdTypesList(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface IEditProfilePresenter{

        void getAttendant(AttendantBO attendantBO, boolean isChanged);
        void saveAttendant(AttendantBO attendantBO);
        void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);

        void signOut();
        void onStart();
        void onStop();
        void onDestroy();
    }

}
