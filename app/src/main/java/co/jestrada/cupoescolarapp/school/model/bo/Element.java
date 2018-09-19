package co.jestrada.cupoescolarapp.school.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Element {

    @JsonProperty("distance") private Distance distance;
    @JsonProperty("duration") private Duration duration;
    @JsonProperty("status") private String status;

    @JsonProperty("distance")
    public Distance getDistance() {
        return distance;
    }

    @JsonProperty("duration")
    public Duration getDuration() {
        return duration;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
}
