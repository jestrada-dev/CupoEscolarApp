package co.jestrada.cupoescolarapp.common.model.modelDocJson;

import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;

public class RelationshipTypeDocJson {

    private String name;

    public RelationshipTypeDocJson() {
    }

    public void setValues (RelationshipTypeBO relationshipTypeBO){
        this.name = relationshipTypeBO.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
