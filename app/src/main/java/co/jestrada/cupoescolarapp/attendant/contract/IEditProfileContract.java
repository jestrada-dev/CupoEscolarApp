package co.jestrada.cupoescolarapp.attendant.contract;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface IEditProfileContract extends IBaseContract {

    interface IEditProfileView{
        void goToLogin();

        void getAttendantTransactionState(boolean successful);

        void setAttendantUI(AttendantBO attendantBO, boolean isChanged);
        void setDocIdTypesList(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);
    }

    interface IEditProfilePresenter{

        void getAttendantTransactionState(boolean successful);

        void getData();

        void getAttendant(AttendantBO attendantBO, boolean isChanged);
        void saveAttendant(AttendantBO attendantBO);
        void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);

        void signOut();
    }

}
