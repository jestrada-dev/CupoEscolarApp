package co.jestrada.cupoescolarapp.common.model.bo;

import co.jestrada.cupoescolarapp.common.model.modelDocJson.GradeDocJson;

public class GradeBO {

    private String grade;
    private String name;

    public GradeBO() {
    }

    public void setValues(GradeDocJson gradeDocJson){
        this.grade = gradeDocJson.getGrade();
        this.name = gradeDocJson.getName();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
