package co.jestrada.cupoescolarapp.attendant.model.modelDocJson;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;

public class RefPositionDocJson {

    private String userUid;
    private String description;
    private double lat;
    private double lng;
    private String address;
    private String city;
    private String adminArea;
    private String postalCode;

    public RefPositionDocJson() {
    }

    public void setValues(RefPositionBO refPositionBO){
        if(refPositionBO != null){
            this.userUid = refPositionBO.getUserUid();
            this.description = refPositionBO.getDescription();
            this.lat = refPositionBO.getLat();
            this.lng = refPositionBO.getLng();
            this.address = refPositionBO.getAddress();
            this.city = refPositionBO.getCity();
            this.adminArea = refPositionBO.getAdminArea();
            this.postalCode = refPositionBO.getPostalCode();
        }
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
