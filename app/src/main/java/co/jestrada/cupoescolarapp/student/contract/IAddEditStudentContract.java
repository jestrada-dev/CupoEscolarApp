package co.jestrada.cupoescolarapp.student.contract;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.base.contract.IBaseContract;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public interface IAddEditStudentContract extends IBaseContract {

    interface IAddEditStudentView {

        void getStudentTransactionState(boolean successful);
        void setStudentUI(StudentBO studentBO, boolean isChanged);
        void setDocIdTypesList(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);
        void setRelationshipTypesList(ArrayList<RelationshipTypeBO> relationshipTypeBOS, boolean isChanged);
    }

    interface IAddEditStudentPresenter {

        void getStudentTransactionState(boolean successful);
        void getData(String docId);
        void getRelationshipTypes(ArrayList<RelationshipTypeBO> relationshipTypeBOS, boolean isChanged);
        void getDocIdTypes(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged);
        void getStudent(StudentBO studentBO, boolean isChanged);
        void saveStudent(StudentBO studentBO);
    }

}
