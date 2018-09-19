package co.jestrada.cupoescolarapp.school.model.modelDocJson;

import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;

public class SchoolDocJson {

    private String code;
    private String name;
    private String director;
    private String address;
    private String calendar;
    private String schoolDays;
    private String grades;
    private boolean concession;
    private boolean official;
    private String email;
    private String phones;
    private String locality;
    private Double lat;
    private Double lng;

    public SchoolDocJson() {
    }

    public void setValues(SchoolBO schoolBO) {
        this.code = schoolBO.getCode();
        this.name = schoolBO.getName();
        this.director = schoolBO.getDirector();
        this.address = schoolBO.getAddress();
        this.calendar = schoolBO.getCalendar();
        this.grades = schoolBO.getGrades();
        this.schoolDays = schoolBO.getSchoolDays();
        this.concession = schoolBO.isConcession();
        this.official = schoolBO.isOfficial();
        this.email = schoolBO.getEmail();
        this.phones = schoolBO.getPhones();
        this.locality = schoolBO.getLocality();
        this.lat = schoolBO.getLat();
        this.lng = schoolBO.getLng();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getSchoolDays() {
        return schoolDays;
    }

    public void setSchoolDays(String schoolDay) {
        this.schoolDays = schoolDay;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public boolean isConcession() {
        return concession;
    }

    public void setConcession(boolean concession) {
        this.concession = concession;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
