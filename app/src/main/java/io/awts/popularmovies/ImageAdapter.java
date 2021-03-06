package io.awts.popularmovies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alexanderwine on 7/16/15.
 */
public class ImageAdapter extends ArrayAdapter<MovieData> {

    private static final String LOG_TAG = ImageAdapter.class.getSimpleName();

    private String size;


    public ImageAdapter(Activity context, List<MovieData> movieData) {
        super(context, 0, movieData);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        size = preferences.getString(context.getString(R.string.pref_poster_size_key), context.getString(R.string.pref_poster_size_default));

    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        MovieData movieData = getItem(postion);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
        }


//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(convertView.getContext());
//        String size =
        String poster_url = MovieData.getImageUrl(movieData.poster_path, size);

        ImageView movieView = (ImageView) convertView.findViewById(R.id.grid_item_poster);
        Picasso.with(getContext()).load(poster_url).into(movieView);

        return convertView;
    }



//    private final Context context;
//    private final List<String> urls = new ArrayList<String>();
//
//    public ImageAdapter(Context context) {
//        this.context = context;
//
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        SquaredImageView view = (SquaredImageView) convertView;
//        if (view == null) {
//            view = new SquaredImageView(context);
//            view.setScaleType(CENTER_CROP);
//        }
//        String url = getItem(position);
//
//        Picasso.with(context)
//                .load(url)
//                .placeholder(R.drawable.ic_image_placeholder)
//                .error(R.drawable.ic_image_placeholder)
//                .fit()
//                .tag(context)
//                .into(view);
//        return view;
//    }
//    @Override public int getCount() {
//        return urls.size();
//    }
//
//    @Override public String getItem(int position) {
//        return urls.get(position);
//    }
//    @Override public long getItemId(int postion) {
//        return postion;
//    }
}
