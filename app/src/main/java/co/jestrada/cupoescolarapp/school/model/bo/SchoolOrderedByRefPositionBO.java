package co.jestrada.cupoescolarapp.school.model.bo;

import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolOrderedByRefPositionDocJson;

public class SchoolOrderedByRefPositionBO {

    private double position;
    private String schoolCode;
    private String name;
    private String distanceText;
    private Double distanceValue;
    private String durationText;
    private Double durationValue;
    private Double lat;
    private Double lng;

    public SchoolOrderedByRefPositionBO() {
    }

    public void setValues(SchoolOrderedByRefPositionDocJson schoolOrderedByRefPositionDocJson){
        this.position = schoolOrderedByRefPositionDocJson.getPosition();
        this.schoolCode = schoolOrderedByRefPositionDocJson.getSchoolCode();
        this.name = schoolOrderedByRefPositionDocJson.getName();
        this.distanceText = schoolOrderedByRefPositionDocJson.getDistanceText();
        this.distanceValue = schoolOrderedByRefPositionDocJson.getDistanceValue();
        this.durationText = schoolOrderedByRefPositionDocJson.getDurationText();
        this.durationValue = schoolOrderedByRefPositionDocJson.getDurationValue();
        this.lat = schoolOrderedByRefPositionDocJson.getLat();
        this.lng = schoolOrderedByRefPositionDocJson.getLng();
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
