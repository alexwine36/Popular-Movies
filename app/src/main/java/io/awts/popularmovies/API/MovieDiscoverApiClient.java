package io.awts.popularmovies.API;

import io.awts.popularmovies.model.discover.MovieDiscoverApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alexanderwine on 8/8/15.
 */
public class MovieDiscoverApiClient {

    private static tmdbApiDetailsInterface apiDetailsInterface;
    public static tmdbApiDetailsInterface getApiDetailsInterface() {
        if (apiDetailsInterface == null){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.themoviedb.org/3/discover")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            apiDetailsInterface = restAdapter.create(tmdbApiDetailsInterface.class);
        }
        return apiDetailsInterface;
    }
    public interface tmdbApiDetailsInterface {
        @GET("/movie")
        void getDetails(@Query("sort_by") String sort_by, @Query("page") String page, @Query("api_key") String api_key, Callback<MovieDiscoverApi> callback);

    }

//    private static TmdbApiInterface apiInterface;
//
//    public static TmdbApiInterface getTmdbApiClient() {
//        if (apiInterface == null) {
//
//
//
//            RestAdapter restAdapter = new RestAdapter.Builder()
//                    .setEndpoint("http://api.themoviedb.org/3/movie")
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                    .build();
//            apiInterface = restAdapter.create(TmdbApiInterface.class);
//        }
//        return apiInterface;
//    }
//    //    ?api_key={ api_key }&append_to_response=reviews,videos
//    public interface TmdbApiInterface {
//        @GET("/{id}")
//        void getDetails(@Path("id") String id, @Query("api_key") String api_key, @Query("append_to_response") String append_to_response, Callback<MovieDiscoverApi> callback);
//    }
}
