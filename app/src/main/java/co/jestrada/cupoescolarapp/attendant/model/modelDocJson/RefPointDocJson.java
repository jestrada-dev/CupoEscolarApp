package co.jestrada.cupoescolarapp.attendant.model.modelDocJson;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;

public class RefPointDocJson {

    private long lat;
    private long lng;
    private String address;
    private String locality;

    public RefPointDocJson() {
    }

    public void setValues(AttendantBO attendantBO){
        if(attendantBO.getRefPoint() != null){
            this.lat = attendantBO.getRefPoint().getLat();
            this.lng = attendantBO.getRefPoint().getLng();
            this.address = attendantBO.getRefPoint().getAddress();
            this.locality = attendantBO.getRefPoint().getLocality();
        }
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
