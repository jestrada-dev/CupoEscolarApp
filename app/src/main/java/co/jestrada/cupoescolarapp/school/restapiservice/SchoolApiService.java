package co.jestrada.cupoescolarapp.school.restapiservice;

import java.util.Map;

import co.jestrada.cupoescolarapp.school.model.bo.DistanceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SchoolApiService {

    @GET("maps/api/distancematrix/json")
    Call<DistanceResponse> getDistance(
            @Query("units") String units,
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("key") String key
    );

}
