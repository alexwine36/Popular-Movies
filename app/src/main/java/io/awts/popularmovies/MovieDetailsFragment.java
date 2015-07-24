package io.awts.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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
        if (intent != null && intent.hasExtra(extra_movie)){
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



        }

        return rootView;
    }
}
