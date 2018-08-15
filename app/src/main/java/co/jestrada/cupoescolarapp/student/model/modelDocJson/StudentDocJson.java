package co.jestrada.cupoescolarapp.student.model.modelDocJson;

import co.jestrada.cupoescolarapp.attendant.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class StudentDocJson {

    private String docId;
    private String docIdType;
    private String lastName;
    private String firstName;
    private GenreEnum genre;
    private String birthdate;
    private String grade;
    private String attendantUserUid;

    public StudentDocJson() {
    }

    public void setValues (StudentBO studentBO){
        this.docId = studentBO.getDocId();
        this.docIdType = studentBO.getDocIdType();
        this.lastName = studentBO.getLastName();
        this.firstName = studentBO.getFirstName();
        this.genre = studentBO.getGenre();
        this.birthdate = studentBO.getBirthdate();
        this.grade = studentBO.getGrade();
        this.attendantUserUid = studentBO.getAttendantUserUid();
    }

    public String getAttendantUserUid() {
        return attendantUserUid;
    }

    public void setAttendantUserUid(String attendantUserUid) {
        this.attendantUserUid = attendantUserUid;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocIdType() {
        return docIdType;
    }

    public void setDocIdType(String docIdType) {
        this.docIdType = docIdType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
