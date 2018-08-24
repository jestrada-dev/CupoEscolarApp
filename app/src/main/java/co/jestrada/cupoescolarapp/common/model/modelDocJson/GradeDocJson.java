package co.jestrada.cupoescolarapp.common.model.modelDocJson;

import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;

public class GradeDocJson {

    private String grade;
    private String name;

    public GradeDocJson() {
    }

    public void setValues(GradeBO gradeBO){
        this.grade = gradeBO.getGrade();
        this.name = gradeBO.getName();
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
