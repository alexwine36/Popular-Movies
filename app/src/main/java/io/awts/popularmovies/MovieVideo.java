package io.awts.popularmovies;

import android.util.Log;

/**
 * Created by alexanderwine on 8/1/15.
 */
public class MovieVideo {
    private String LOG_TAG = MovieVideo.class.getSimpleName();

    String id;
    String iso;
    String key;
    String name;
    String site;
    String size;
    String type;

    public MovieVideo(String mId, String mIso, String mKey, String mName, String mSite, String mSize, String mType) {
        this.id = mId;
        Log.d(LOG_TAG + " ID:", id);
        this.iso = mIso;
        Log.d(LOG_TAG + " ISO:", iso);
        this.key = mKey;
        Log.d(LOG_TAG + " Key:", key);
        this.name = mName;
        Log.d(LOG_TAG + " Name:", name);
        this.site = mSite;
        Log.d(LOG_TAG + " Site:", site);
        this.size = mSize;
        Log.d(LOG_TAG + " Size:", size);
        this.type = mType;
        Log.d(LOG_TAG + " Type:", type);
    }

}
