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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
public class DiscoveryFragment extends Fragment {

    private String TAG = DiscoveryFragment.class.getSimpleName();

    public ImageAdapter mMovieAdapter;

    private ArrayList<MovieData> movieList;

    private String prefSort;

    public Integer page_int;



    private static final String MOVIE_KEY = "movies";

    private static final String PAGE_KEY = "page";



    public DiscoveryFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_KEY, movieList);
        outState.putInt(PAGE_KEY, page_int);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
// TODO: Add a listener for settings change

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        String sort_value = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));

//        prefSort = sort_value;
//        if (sort_value != prefSort && prefSort != null) {
//            prefSort = sort_value;
//            updateMovies();
//        }


        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_KEY) || !savedInstanceState.containsKey(PAGE_KEY)) {
            movieList = new ArrayList<MovieData>();
            page_int = 1;
//            prefSort = sort_value;
            updateMovies();
        } else {
            movieList = savedInstanceState.getParcelableArrayList(MOVIE_KEY);
            page_int = savedInstanceState.getInt(PAGE_KEY);
        }
        setHasOptionsMenu(true);



        mMovieAdapter = new ImageAdapter(getActivity(), movieList);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = mMovieAdapter.getItem(position).title;
                String extra_movie = getString(R.string.extra_movie);
                Toast.makeText(getActivity(), movie, Toast.LENGTH_SHORT).show();

                MovieData movieData = mMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetails.class);
                intent.putExtra(extra_movie, movieData);
                startActivity(intent);
//                intent.putExtra(MOVIE_KEY, MovieData.class);
            }
        });


        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                scrollStateChanged = true;
                Integer scroller = scrollState;
//                Log.d(TAG, scroller.toString());


                Log.d(TAG + " Scroll State:", scroller.toString());

                if (scrollState == 0) {
                    page_int++;
                    updateMovies();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        return view;

    }


    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
    }

//    public void onStart() {
//        super.onStart();
//        if (page_int == 1 && movieList != null) {
//            updateMovies();
//        }
//        if (page_int == null) {
//            page_int = 1;
//            if (movieList == null) {
//                updateMovies();
//            }
//        }
//    }

    public class FetchMoviesTask extends AsyncTask<Void, Void, Void> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private Void getMovieDataFromJson(String movieJsonStr) throws JSONException {
//            final String TMDB_PAGE = "page";
            final String TMDB_RESULTS = "results";
            final String TMDB_TITLE = "title";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_BACKDROP = "backdrop_path";
            final String TMDB_RELEASE = "release_date";
            final String TMDB_VOTE = "vote_average";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(TMDB_RESULTS);

            String[] resultStrs = new String[movieArray.length()];
            for (int i = 0; i < movieArray.length(); i++) {
                String backdrop_path;
                String title;
                String overview;
                String release_date;
                String poster_path;
                String vote_average;

                JSONObject movieInfo = movieArray.getJSONObject(i);

                title = movieInfo.getString(TMDB_TITLE);
                overview = movieInfo.getString(TMDB_OVERVIEW);
                backdrop_path = movieInfo.getString(TMDB_BACKDROP);
                release_date = movieInfo.getString(TMDB_RELEASE);
                vote_average = movieInfo.getString(TMDB_VOTE);
                poster_path = movieInfo.getString(TMDB_POSTER);


                MovieData movieData = new MovieData(backdrop_path, title, overview, release_date, poster_path, vote_average);

                movieList.add(movieData);

//                if (MovieList != null) {
//                    MovieList.add(new MovieData(backdrop_path, title, overview, release_date, poster_path, vote_average));
//                    Log.d(LOG_TAG, "Movie List Item added");
//                } else {
//                    Log.d(LOG_TAG, "Movie List is null");
//                    MovieList = new ArrayList<MovieData>();
//                    MovieList.add(new MovieData(backdrop_path, title, overview, release_date, poster_path, vote_average));
//                }


//                resultStrs[i] = title + " - " + poster_path + " - " + overview;

                resultStrs[i] = poster_path;

//                Log.v(LOG_TAG, title + " - " + poster_path + " - " + overview);

//                JSONObject movieObject = movieInfo.getJSONObject()
            }
//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Movie entry: " + s);
//            }
//            return movieList;
            return null;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.

            String moviesJsonStr = null;
//            String sort_by = prefSort;
            final String api_key = getString(R.string.API_Key);


            try {
                String page = page_int.toString();
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
//                final String QUERY_PARAM = "?";
                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendQueryParameter(SORT_PARAM, prefSort).appendQueryParameter(PAGE_PARAM, page).appendQueryParameter(API_PARAM, api_key).build();

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
                    getMovieDataFromJson(moviesJsonStr);
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

        @Override
        protected void onPostExecute(Void params) {
            mMovieAdapter.notifyDataSetChanged();
        }
//        @Override
//        protected void onPostExecute(ArrayList<MovieData> movieDatas) {
//            if (movieDatas != null) {
//                mMovieAdapter.clear();
//
//                mMovieAdapter.addAll(movieDatas);
//            }
//        }


    }

}
