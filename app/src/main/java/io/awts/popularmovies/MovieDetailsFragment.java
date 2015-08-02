package io.awts.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private MovieData mMovie;


    public MovieDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        String prefPosterSize = preferences.getString(getString(R.string.pref_poster_size_key), getString(R.string.pref_poster_size_default));
        String prefBackdropSize = preferences.getString(getString(R.string.pref_backdrop_size_key), getString(R.string.pref_backdrop_size_default));
        Intent intent = getActivity().getIntent();
        String extra_movie = getString(R.string.extra_movie);
        if (intent != null && intent.hasExtra(extra_movie)) {
            mMovie = intent.getParcelableExtra(extra_movie);
            ((TextView) rootView.findViewById(R.id.hello_text)).setText(mMovie.title);

            ImageView backgroundView = (ImageView) rootView.findViewById(R.id.backdrop_image);
            ImageView posterView = (ImageView) rootView.findViewById(R.id.poster_image);
            ImageView tmdbView = (ImageView) rootView.findViewById(R.id.tmdb_img);
            TextView ratingValue = (TextView) rootView.findViewById(R.id.rating_value);
            TextView releaseValue = (TextView) rootView.findViewById(R.id.release_value);
            TextView synopsisValue = (TextView) rootView.findViewById(R.id.sysnopsis_value);
            synopsisValue.setText(mMovie.overview);
            releaseValue.setText(mMovie.release_date);
            ratingValue.setText(mMovie.vote_average);


            String poster_url = MovieData.getImageUrl(mMovie.poster_path, prefPosterSize);

            String backdrop_url = MovieData.getImageUrl(mMovie.backdrop_path, prefBackdropSize);

            Picasso.with(rootView.getContext()).load(backdrop_url).into(backgroundView);
//            Picasso.with(rootView.getContext()).load(mMovie.poster_url).into(posterView);
            Picasso.with(rootView.getContext()).load(poster_url).into(posterView);
            Picasso.with(rootView.getContext()).load(MovieData.provider).into(tmdbView);

            FetchMovieDetailsTask detailsTask = new FetchMovieDetailsTask();
            detailsTask.execute(mMovie.id);

        }
        return rootView;
    }

    public class FetchMovieDetailsTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = FetchMovieDetailsTask.class.getSimpleName();
//TODO: Implement Retrofit to siplify Api calls
        private Void getMovieDetailsFromJson(String movieJsonStr) throws JSONException {
//            final String TMDB_PAGE = "page";
            ArrayList<MovieReview> reviewArrayList = new ArrayList<MovieReview>();
            ArrayList<MovieVideo> videoArrayList = new ArrayList<MovieVideo>();
            final String TMDB_REVIEWS = "reviews";
            final String TMDB_VIDEOS = "videos";
            final String TMDB_RESULTS = "results";
            final String TMDB_AUTHOR = "author";
            final String TMDB_CONTENT = "content";
            final String TMDB_URL = "url";
            final String TMDB_ID = "id";
            final String TMDB_ISO = "iso_639_1";
            final String TMDB_KEY = "key";
            final String TMDB_NAME = "name";
            final String TMDB_SITE = "site";
            final String TMDB_SIZE = "size";
            final String TMDB_TYPE = "type";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONObject videos = movieJson.getJSONObject(TMDB_VIDEOS);
            JSONObject reviews = movieJson.getJSONObject(TMDB_REVIEWS);
            JSONArray movieVideos = videos.getJSONArray(TMDB_RESULTS);
            JSONArray movieReviews = reviews.getJSONArray(TMDB_RESULTS);

            String[] resultStrs = new String[movieReviews.length()];
            Integer reviewCount = movieReviews.length();
            Log.d(LOG_TAG + " Reviews Count:", reviewCount.toString());
            for (int i = 0; i < movieReviews.length(); i++) {
                String id;
                String url;
                String content;
                String author;


                JSONObject movieInfo = movieReviews.getJSONObject(i);

                author = movieInfo.getString(TMDB_AUTHOR);
                content = movieInfo.getString(TMDB_CONTENT);
                url = movieInfo.getString(TMDB_URL);
                id = movieInfo.getString(TMDB_ID);


                MovieReview movieReview = new MovieReview(id, author, content, url);
                reviewArrayList.add(movieReview);

                resultStrs[i] = author + " - " + content + " - " + url + " - " + id;
            }
            Integer videoCount = movieVideos.length();
            Log.d(LOG_TAG + " Videos Count:", videoCount.toString());
            for (int i = 0; i < videoCount; i++) {
                String id;
                String iso;
                String key;
                String name;
                String site;
                String size;
                String type;


                JSONObject movieInfo = movieVideos.getJSONObject(i);

                id = movieInfo.getString(TMDB_ID);
                iso = movieInfo.getString(TMDB_ISO);
                key = movieInfo.getString(TMDB_KEY);
                name = movieInfo.getString(TMDB_NAME);
                site = movieInfo.getString(TMDB_SITE);
                size = movieInfo.getString(TMDB_SIZE);
                type = movieInfo.getString(TMDB_TYPE);

                MovieVideo movieVideo = new MovieVideo(id,iso,key,name,site,size,type);
                videoArrayList.add(movieVideo);

            }
//            for (String s : resultStrs) {
//                Log.d(LOG_TAG, "Movie entry: " + s);
//            }
            return null;
        }

        @Override
        protected Void doInBackground(String... id_param) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.

            String moviesJsonStr = null;
//            String sort_by = prefSort;
            final String api_key = getString(R.string.API_Key);
            final String append_str = "reviews,videos";
            String id = id_param[0];

            try {
//                String page = page_int.toString();


                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
//                final String QUERY_PARAM = "?";
//                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";
//                final String PAGE_PARAM = "page";
                final String APPEND_PARAM = "append_to_response";


                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendEncodedPath(id).appendQueryParameter(API_PARAM, api_key).appendQueryParameter(APPEND_PARAM, append_str).build();

//                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=");

                URL url = new URL(builtUri.toString());


                Log.d(LOG_TAG, builtUri.toString());


                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.d("InputStream", "Input Stream is null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");

                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();

                Log.d("JSON", moviesJsonStr);
                try {
                    getMovieDetailsFromJson(moviesJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(Void params) {
//            mMovieAdapter.notifyDataSetChanged();
//        }
    }

}
