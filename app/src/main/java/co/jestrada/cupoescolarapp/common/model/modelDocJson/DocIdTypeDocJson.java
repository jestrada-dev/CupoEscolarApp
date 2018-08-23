package co.jestrada.cupoescolarapp.common.model.modelDocJson;

import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;

public class DocIdTypeDocJson {

    private String shortName;
    private String longName;

    public DocIdTypeDocJson() {
    }

    public void setValues(DocIdTypeBO docIdTypeBO){
        this.shortName = docIdTypeBO.getShortName();
        this.longName = docIdTypeBO.getLongName();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
