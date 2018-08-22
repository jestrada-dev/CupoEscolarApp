package co.jestrada.cupoescolarapp.attendant.model.bo;

import java.util.List;

import co.jestrada.cupoescolarapp.attendant.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.AttendantDocJson;
import co.jestrada.cupoescolarapp.attendant.model.enums.StateUserEnum;

public class AttendantBO {

    private static volatile AttendantBO attendantBOModelInstance = new AttendantBO();

    private String userUid;
    private String photoURL;
    private StateUserEnum state;
    private boolean onSession;
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
    private List<String> students;

    private AttendantBO() {
        if(attendantBOModelInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static AttendantBO getInstance(){
        if (attendantBOModelInstance == null){
            synchronized (AttendantBO.class) {
                if (attendantBOModelInstance == null){
                    attendantBOModelInstance = new AttendantBO();
                }
            }
        }
        return attendantBOModelInstance;
    }

    public void setValues(AttendantDocJson attendantDocJson){
        if (attendantDocJson != null){
            this.userUid = attendantDocJson.getUserUid();
            this.state = attendantDocJson.getState();
            this.docId = attendantDocJson.getDocId();
            this.docIdType = attendantDocJson.getDocIdType();
            this.lastName = attendantDocJson.getLastName();
            this.firstName = attendantDocJson.getFirstName();
            this.genre = attendantDocJson.getGenre();
            this.birthdate = attendantDocJson.getBirthdate();
            this.address = attendantDocJson.getAddress();
            this.email = attendantDocJson.getEmail();
            this.localPhone = attendantDocJson.getLocalPhone();
            this.mobilePhone = attendantDocJson.getMobilePhone();
        }
    }

    public void addStudent(String studentDocIdJson){
        this.students.add(studentDocIdJson);
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public StateUserEnum getState() {
        return state;
    }

    public void setState(StateUserEnum state) {
        this.state = state;
    }

    public boolean isOnSession() {
        return onSession;
    }

    public void setOnSession(boolean onSession) {
        this.onSession = onSession;
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

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }
}
