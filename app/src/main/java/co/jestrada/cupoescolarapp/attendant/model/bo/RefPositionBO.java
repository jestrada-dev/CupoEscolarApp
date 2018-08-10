package co.jestrada.cupoescolarapp.attendant.model.bo;

import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPositionDocJson;

public class RefPositionBO {

    private String userUid;
    private String description;
    private Double lat;
    private Double lng;
    private String address;
    private String city;
    private String adminArea;
    private String postalCode;

    public RefPositionBO() {
    }

    public void setValues(RefPositionDocJson refPositionDocJson){
        this.userUid = refPositionDocJson.getUserUid();
        this.description = refPositionDocJson.getDescription();
        this.lat = refPositionDocJson.getLat();
        this.lng = refPositionDocJson.getLng();
        this.address = refPositionDocJson.getAddress();
        this.city = refPositionDocJson.getCity();
        this.adminArea = refPositionDocJson.getAdminArea();
        this.postalCode = refPositionDocJson.getPostalCode();
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
