package io.awts.popularmovies;

import android.util.Log;

/**
 * Created by alexanderwine on 7/18/15.
 */
public class MovieData {

    private String LOG_TAG = MovieData.class.getSimpleName();

    String backdrop_path;
    String title;
    String overview;
    String release_date;
    String poster_path;
    String vote_average;
    String poster_url;


    public MovieData(String bPath, String title, String overview, String rDate, String pPath, String vAverage) {
        this.backdrop_path = bPath;
        Log.d(LOG_TAG + " Backdrop Path:", backdrop_path);
        this.title = title;
        Log.d(LOG_TAG + " Title:", title);
        this.overview = overview;
        Log.d(LOG_TAG + " Overview:", overview);
        this.release_date = rDate;
        Log.d(LOG_TAG + " Release Date:", release_date);
        this.poster_path = pPath;
        Log.d(LOG_TAG + " Poster Path:", poster_path);
        this.vote_average = vAverage;
        Log.d(LOG_TAG + " Vote Average:", vote_average);
        this.poster_url = getImageUrl(poster_path, "w342");
        Log.d(LOG_TAG + " Poster URL:", poster_url);
    }

    public String getImageUrl(String image, String size) {
        final String MOVIE_BASE_URL = "http://image.tmdb.org/t/p/";

        String imageUrl = MOVIE_BASE_URL + size + image;
        return imageUrl;

    }

}
