package co.jestrada.cupoescolarapp.school.model.bo;

import co.jestrada.cupoescolarapp.school.model.modelDocJson.SchoolOrderedByRefPositionDocJson;

public class SchoolOrderedByRefPositionBO {

    private double position;
    private double schoolId;
    private String distanceText;
    private Double distanceValue;
    private String durationText;
    private Double durationValue;

    public SchoolOrderedByRefPositionBO() {
    }

    public void setValues(SchoolOrderedByRefPositionDocJson schoolOrderedByRefPositionDocJson){
        this.position = schoolOrderedByRefPositionDocJson.getPosition();
        this.schoolId = schoolOrderedByRefPositionDocJson.getSchoolId();
        this.distanceText = schoolOrderedByRefPositionDocJson.getDistanceText();
        this.distanceValue = schoolOrderedByRefPositionDocJson.getDistanceValue();
        this.durationText = schoolOrderedByRefPositionDocJson.getDurationText();
        this.durationValue = schoolOrderedByRefPositionDocJson.getDurationValue();
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(double schoolId) {
        this.schoolId = schoolId;
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
}
