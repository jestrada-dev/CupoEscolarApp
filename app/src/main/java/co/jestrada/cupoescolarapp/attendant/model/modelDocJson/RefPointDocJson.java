package co.jestrada.cupoescolarapp.attendant.model.modelDocJson;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;

public class RefPointDocJson {

    private String description;
    private Double lat;
    private Double lng;
    private String address;
    private String locality;

    public RefPointDocJson() {
    }

    public void setValues(AttendantBO attendantBO){
        if(attendantBO.getRefPoint() != null){
            this.description = attendantBO.getRefPoint().getDescription();
            this.lat = attendantBO.getRefPoint().getLat();
            this.lng = attendantBO.getRefPoint().getLng();
            this.address = attendantBO.getRefPoint().getAddress();
            this.locality = attendantBO.getRefPoint().getLocality();
        }
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
