package co.jestrada.cupoescolarapp.student.contract;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface IEditStudentContract extends IBaseContract {

    interface IEditStudentView{

        void getStudentTransactionState(boolean successful);
        void setStudentUI(StudentBO studentBO, boolean isChanged);
    }

    interface IEditStudentPresenter{

        void getStudentTransactionState(boolean successful);
        void getData(String docId);

        void getStudent(StudentBO studentBO, boolean isChanged);
        void saveStudent(StudentBO studentBO);
    }

}
