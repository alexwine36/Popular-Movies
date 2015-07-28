package io.awts.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by alexanderwine on 7/18/15.
 */
public class MovieData implements Parcelable {

    private String LOG_TAG = MovieData.class.getSimpleName();

    public static String[] sizes = {
            "w92", "w154", "w185", "w342", "w500", "w780", "original"
    };

    public static String provider = "https://d3a8mw37cqal2z.cloudfront.net/images/logos/var_8_0_tmdb-logo-2_Bree.png";

    String backdrop_path;
    String title;
    String overview;
    String release_date;
    String poster_path;
    String vote_average;
    String poster_url;
    String backdrop_url;
    String id;



    public MovieData(String bPath, String title, String overview, String rDate, String pPath, String vAverage, String id) {
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

        this.id = id;
        Log.d(LOG_TAG + " Movie ID:", id);

        this.poster_url = getImageUrl(poster_path, "w185");
        Log.d(LOG_TAG + " Poster URL:", poster_url);
        this.backdrop_url = getImageUrl(backdrop_path, "w780");
        Log.d(LOG_TAG + " Backdrop URL:", backdrop_url);
    }

    private MovieData(Parcel in) {
        backdrop_path = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        vote_average = in.readString();
        id = in.readString();
        poster_url = in.readString();
        backdrop_url = in.readString();
    }

    public static String getImageUrl(String image, String size) {
        final String MOVIE_BASE_URL = "http://image.tmdb.org/t/p/";

        String imageUrl = MOVIE_BASE_URL + size + image;
        return imageUrl;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return backdrop_path + "--" + title + "--" + overview + "--" + release_date
                + "--" + poster_path + "--" + vote_average + "--" + id + "--" + poster_url +
                "--" + backdrop_url;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdrop_path);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(poster_path);
        parcel.writeString(vote_average);
        parcel.writeString(id);
        parcel.writeString(poster_url);
        parcel.writeString(backdrop_url);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel parcel) {
            return new MovieData(parcel);
        }

        @Override
        public MovieData[] newArray(int i) {
            return new MovieData[i];
        }
    };


}
