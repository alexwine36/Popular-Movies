package io.awts.popularmovies;

import android.util.Log;

/**
 * Created by alexanderwine on 7/30/15.
 */
public class MovieReview {
    private  String LOG_TAG = MovieReview.class.getSimpleName();


    String id;
    String author;
    String content;
    String url;

    public MovieReview (String mId, String mAuthor, String mContent, String mUrl) {
        this.id = mId;
        Log.d(LOG_TAG, id);
        this.author = mAuthor;
        Log.d(LOG_TAG, author);
        this.content = mContent;
        Log.d(LOG_TAG, content);
        this.url = mUrl;
        Log.d(LOG_TAG, url);
    }
}
