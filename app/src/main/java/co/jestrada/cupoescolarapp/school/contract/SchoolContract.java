package co.jestrada.cupoescolarapp.school.contract;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface SchoolContract extends IBaseContract {

    interface ISchoolView {

        void setStudentList(ArrayList<StudentBO> studentBO, boolean isChanged);
        void setSchoolUI(SchoolBO schoolBO, boolean isChanged);
        void getEnrollStudentTransactionState( boolean succesful);
    }

    interface ISchoolPresenter {

        void getEnrollStudentTransactionState( boolean succesful);
        void getData(String schoolCode);
        void getSchool(SchoolBO schoolBO, boolean isChanged);
        void getStudents(ArrayList<StudentBO> studentBOS, boolean isChanged);
    }

}
