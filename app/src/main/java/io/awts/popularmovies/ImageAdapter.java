package io.awts.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by alexanderwine on 7/16/15.
 */
public class ImageAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();

    public ImageAdapter(Context context) {
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }
        String url = getItem(position);

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .fit()
                .tag(context)
                .into(view);
        return view;
    }
    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }
    @Override public long getItemId(int postion) {
        return postion;
    }
}
