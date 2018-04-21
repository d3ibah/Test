package by.test2rest.internet;


import by.test2rest.internet.get.Ref;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiRef {

    @GET("services/rest")
    Observable<Ref> getRef(@Query("method") String refMethod, @Query("api_key") String refApiKey,
                           @Query("extras") String refExtras, @Query("per_page") int refPerpage,
                           @Query("format") String refFormat, @Query("nojsoncallback") int refNojsoncallback);
}
