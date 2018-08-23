package co.jestrada.cupoescolarapp.common.model.bo;

import co.jestrada.cupoescolarapp.common.model.modelDocJson.RelationshipTypeDocJson;

public class RelationshipTypeBO {

    private String name;

    public RelationshipTypeBO() {
    }

    public void setValues(RelationshipTypeDocJson relationshipTypeDocJson){
        this.name = relationshipTypeDocJson.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
