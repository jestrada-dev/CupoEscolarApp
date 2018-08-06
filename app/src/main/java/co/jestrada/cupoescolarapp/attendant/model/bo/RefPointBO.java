package co.jestrada.cupoescolarapp.attendant.model.bo;

import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPointDocJson;

public class RefPointBO {

    private String description;
    private Double lat;
    private Double lng;
    private String address;
    private String locality;

    public RefPointBO() {
    }

    public void setValues(RefPointDocJson refPointDocJson){
        this.description = refPointDocJson.getDescription();
        this.lat = refPointDocJson.getLat();
        this.lng = refPointDocJson.getLng();
        this.address = refPointDocJson.getAddress();
        this.locality = refPointDocJson.getLocality();
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
