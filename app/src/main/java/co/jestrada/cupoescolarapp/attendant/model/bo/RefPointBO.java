package co.jestrada.cupoescolarapp.attendant.model.bo;

import co.jestrada.cupoescolarapp.attendant.model.modelDocJson.RefPointDocJson;

public class RefPointBO {

    private long lat;
    private long lng;
    private String address;
    private String locality;

    public RefPointBO() {
    }

    public void setValues(RefPointDocJson refPointDocJson){
        this.lat = refPointDocJson.getLat();
        this.lng = refPointDocJson.getLng();
        this.address = refPointDocJson.getAddress();
        this.locality = refPointDocJson.getLocality();
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
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
