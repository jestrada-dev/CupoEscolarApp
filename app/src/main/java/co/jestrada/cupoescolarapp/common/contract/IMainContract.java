package co.jestrada.cupoescolarapp.common.contract;

import java.util.List;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface IMainContract extends IBaseContract {

    interface IMainView{

        void getRefPositionTransactionState(boolean successful);
        void getAttendantTransactionState(boolean successful);

        void getSchoolsListOrdered(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS,
                                   boolean isChanged);
        void goToLogin();
        void setNavViewUI(boolean isChanged);
        void setRefPosition(RefPositionBO refPositionBO, boolean isChanged);
    }

    interface IMainPresenter{
        void signOut();

        void getUser(boolean isChanged);
        void getAttendant(boolean isChanged);
        void getRefPosition(RefPositionBO refPositionBO, boolean isChanged);
        void getStudent(StudentBO studentBO, boolean isChanged);
        void getSchools(List<SchoolBO> schoolBOS, boolean isChanged);
        void getSchoolsListOrdered(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS,
                                   boolean isChanged);

        void getRefPositionTransactionState(boolean successful);
        void getAttendantTransactionState(boolean successful);
        void getStudentTransactionState(boolean successful);

    }

}
