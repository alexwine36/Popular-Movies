package io.awts.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.awts.popularmovies.model.ReviewResult;

/**
 * Created by alexanderwine on 8/3/15.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewResult> {

    public ReviewAdapter(Activity context, List<ReviewResult> reviewResult) {
        super(context, 0, reviewResult);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewResult review_result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, parent, false);
        }
        TextView reviewAuthor = (TextView) convertView.findViewById(R.id.review_author);
        reviewAuthor.setText(review_result.getAuthor());
        TextView reviewContent = (TextView) convertView.findViewById(R.id.review_content);
        reviewContent.setText(review_result.getContent());
        return convertView;
    }
}

//    private static final String LOG_TAG = ImageAdapter.class.getSimpleName();
//
//    private String size;
//    public ImageAdapter(Activity context, List<MovieData> movieData) {
//        super(context, 0, movieData);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        size = preferences.getString(context.getString(R.string.pref_poster_size_key), context.getString(R.string.pref_poster_size_default));
//    }
//    @Override
//    public View getView(int postion, View convertView, ViewGroup parent) {
//        MovieData movieData = getItem(postion);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
//        }
//        String poster_url = MovieData.getImageUrl(movieData.poster_path, size);
//        ImageView movieView = (ImageView) convertView.findViewById(R.id.grid_item_poster);
//        Picasso.with(getContext()).load(poster_url).into(movieView);
//        return convertView;
//    }
//}
