package co.jestrada.cupoescolarapp.common.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface IMainContract extends IBaseContract {

    interface IMainView{

        void getRefPositionTransactionState(boolean successful);
        void getAttendantTransactionState(boolean successful);
        void goToLogin();
        void setNavViewUI(boolean isChanged);
    }

    interface IMainPresenter{
        void signOut();

        void getUser(boolean isChanged);
        void getAttendant(boolean isChanged);
        void getRefPosition(RefPositionBO refPositionBO, boolean isChanged);
        void getStudent(StudentBO studentBO, boolean isChanged);

        void getRefPositionTransactionState(boolean successful);
        void getAttendantTransactionState(boolean successful);
        void getStudentTransactionState(boolean successful);

    }

}
