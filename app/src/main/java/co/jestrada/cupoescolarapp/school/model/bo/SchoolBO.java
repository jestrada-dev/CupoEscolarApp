package co.jestrada.cupoescolarapp.school.model.bo;

import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolDocJson;

public class SchoolBO {

    private String code;
    private String name;
    private String director;
    private String address;
    private String calendar;
    private boolean concession;
    private boolean official;
    private String email;
    private String phones;
    private String locality;
    private String postalCodeLocality;
    private Double lat;
    private Double lng;

    public SchoolBO() {
    }

    public void setValues(SchoolDocJson schoolDocJson) {
        this.code = schoolDocJson.getCode();
        this.name = schoolDocJson.getName();
        this.director = schoolDocJson.getDirector();
        this.address = schoolDocJson.getAddress();
        this.calendar = schoolDocJson.getCalendar();
        this.concession = schoolDocJson.isConcession();
        this.official = schoolDocJson.isOfficial();
        this.email = schoolDocJson.getEmail();
        this.phones = schoolDocJson.getPhones();
        this.locality = schoolDocJson.getLocality();
        this.postalCodeLocality = schoolDocJson.getPostalCodeLocality();
        this.lat = schoolDocJson.getLat();
        this.lng = schoolDocJson.getLng();
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

    public String getPostalCodeLocality() {
        return postalCodeLocality;
    }

    public void setPostalCodeLocality(String postalCodeLocality) {
        this.postalCodeLocality = postalCodeLocality;
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
