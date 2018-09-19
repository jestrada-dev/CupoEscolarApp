package co.jestrada.cupoescolarapp.school.model.modelDocJson;

import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;

public class SchoolOrderedByRefPositionDocJson {

    private double position;
    private String schoolCode;
    private String name;
    private String distanceText;
    private Double distanceValue;
    private String durationText;
    private Double durationValue;
    private Double lat;
    private Double lng;

    public SchoolOrderedByRefPositionDocJson() {
    }

    public void setValues(SchoolOrderedByRefPositionBO schoolOrderedByRefPositionBO){
        this.position = schoolOrderedByRefPositionBO.getPosition();
        this.schoolCode = schoolOrderedByRefPositionBO.getSchoolCode();
        this.name = schoolOrderedByRefPositionBO.getName();
        this.distanceText = schoolOrderedByRefPositionBO.getDistanceText();
        this.distanceValue = schoolOrderedByRefPositionBO.getDistanceValue();
        this.durationText = schoolOrderedByRefPositionBO.getDurationText();
        this.durationValue = schoolOrderedByRefPositionBO.getDurationValue();
        this.lat = schoolOrderedByRefPositionBO.getLat();
        this.lng = schoolOrderedByRefPositionBO.getLng();
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public Double getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(Double distanceValue) {
        this.distanceValue = distanceValue;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public Double getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(Double durationValue) {
        this.durationValue = durationValue;
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
