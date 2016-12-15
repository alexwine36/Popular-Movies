package io.awts.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.awts.popularmovies.R;
import io.awts.popularmovies.model.details.ReviewResult;

/**
 * Created by alexanderwine on 8/9/15.
 */
public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerReviewAdapter.ViewHolder> {
    // Container for a dynamically typed data value. Primarily used with Resources for holding Resource Values
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private ArrayList<ReviewResult> mReviewResults;
    private Context context;

    private final String LOG_TAG = RecyclerReviewAdapter.class.getSimpleName();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundAuthorString;
        public String mBoundContentString;

        public View mView;
        public TextView mAuthorView;
        public TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.mAuthorView = (TextView) view.findViewById(R.id.review_list_author);
            this.mContentView = (TextView) view.findViewById(R.id.review_list_content);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAuthorView.getText();
        }
    }

    public ReviewResult getValueAt(int position) {
        return mReviewResults.get(position);
    }

    public RecyclerReviewAdapter(Context context, ArrayList<ReviewResult> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        this.mReviewResults = items;

        this.context = context;
        Integer reviewSize = mReviewResults.size();
        Log.d(LOG_TAG + " ArrayList Size:", reviewSize.toString());

    }

    @Override
    public RecyclerReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.review_list_item, parent, false);
//        view.setBackgroundResource(mBackground);
        return new RecyclerReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ReviewResult reviewResult = getValueAt(position);

        holder.mBoundAuthorString = mReviewResults.get(position).getAuthor();
        Log.d(LOG_TAG + " Author String:", holder.mBoundAuthorString);
        holder.mBoundContentString = mReviewResults.get(position).getContent();
        Log.d(LOG_TAG + " Content String:", holder.mBoundContentString);

        holder.mAuthorView.setText(reviewResult.getAuthor());
        holder.mContentView.setText(reviewResult.getContent());

    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }
}
