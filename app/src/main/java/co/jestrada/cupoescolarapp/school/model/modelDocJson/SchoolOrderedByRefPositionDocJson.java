package co.jestrada.cupoescolarapp.school.model.modelDocJson;

import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;

public class SchoolOrderedByRefPositionDocJson {

    private double position;
    private double schoolId;
    private String distanceText;
    private Double distanceValue;
    private String durationText;
    private Double durationValue;

    public SchoolOrderedByRefPositionDocJson() {
    }

    public void setValues(SchoolOrderedByRefPositionBO schoolOrderedByRefPositionBO){
        this.position = schoolOrderedByRefPositionBO.getPosition();
        this.schoolId = schoolOrderedByRefPositionBO.getSchoolId();
        this.distanceText = schoolOrderedByRefPositionBO.getDistanceText();
        this.distanceValue = schoolOrderedByRefPositionBO.getDistanceValue();
        this.durationText = schoolOrderedByRefPositionBO.getDurationText();
        this.durationValue = schoolOrderedByRefPositionBO.getDurationValue();
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
