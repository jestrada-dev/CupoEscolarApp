package co.jestrada.cupoescolarapp.school.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Duration {

    @JsonProperty("text") private String text;
    @JsonProperty("value") private Integer value;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }
}
