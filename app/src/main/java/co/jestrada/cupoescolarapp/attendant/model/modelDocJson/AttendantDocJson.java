package co.jestrada.cupoescolarapp.attendant.model.modelDocJson;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.enums.GenreEnum;

public class AttendantDocJson {

    private String userUid;
    private String docId;
    private String docIdType;
    private String lastName;
    private String firstName;
    private GenreEnum genre;
    private String birthdate;
    private String address;
    private String email;
    private String localPhone;
    private String mobilePhone;

    public AttendantDocJson() {
    }

    public void setValues (AttendantBO attendantBO){
        this.userUid = attendantBO.getUserUid();
        this.docId = attendantBO.getDocId();
        this.docIdType = attendantBO.getDocIdType();
        this.lastName = attendantBO.getLastName();
        this.firstName = attendantBO.getFirstName();
        this.genre = attendantBO.getGenre();
        this.birthdate = attendantBO.getBirthdate();
        this.address = attendantBO.getAddress();
        this.email = attendantBO.getEmail();
        this.localPhone = attendantBO.getLocalPhone();
        this.mobilePhone = attendantBO.getMobilePhone();
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalPhone() {
        return localPhone;
    }

    public void setLocalPhone(String localPhone) {
        this.localPhone = localPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
