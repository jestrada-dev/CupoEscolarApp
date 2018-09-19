package co.jestrada.cupoescolarapp.school.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistanceResponse {

    @JsonProperty("destination_addresses") private List<String> destinationAddresses = null;
    @JsonProperty("origin_addresses") private List<String> originAddresses = null;
    @JsonProperty("rows") private List<Row> rows = null;
    @JsonProperty("status") private String status;

    @JsonProperty("destination_addresses")
    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    @JsonProperty("origin_addresses")
    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    @JsonProperty("rows")
    public List<Row> getRows() {
        return rows;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
}
