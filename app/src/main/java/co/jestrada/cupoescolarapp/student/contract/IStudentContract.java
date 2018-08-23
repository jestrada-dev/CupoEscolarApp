package co.jestrada.cupoescolarapp.student.contract;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface IStudentContract extends IBaseContract {

    interface IStudentView{

        void getStudentTransactionState(boolean successful);
        void setStudentsUI(ArrayList<StudentBO> studentBOS, boolean isChanged);
    }

    interface IStudentPresenter{

        void getStudentTransactionState(boolean successful);
        void getData();

        void getStudent(StudentBO studentBO, boolean isChanged);
        void getStudentsByAttendantUserUid(ArrayList<StudentBO> studentBOS, boolean isChanged);
        void saveStudent(StudentBO studentBO);
    }

}
