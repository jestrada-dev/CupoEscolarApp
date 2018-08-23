package co.jestrada.cupoescolarapp.common.model.bo;

import co.jestrada.cupoescolarapp.common.model.modelDocJson.DocIdTypeDocJson;

public class DocIdTypeBO {

    private String shortName;
    private String longName;

    public DocIdTypeBO() {
    }

    public void setValues(DocIdTypeDocJson docIdTypeDocJson){
        this.shortName = docIdTypeDocJson.getShortName();
        this.longName = docIdTypeDocJson.getLongName();
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
