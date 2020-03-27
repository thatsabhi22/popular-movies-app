package com.udacity.popularmoviesstage2app.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.models.Review;

import java.util.Collections;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Review> reviews = Collections.emptyList();

    public ReviewAdapter(Context mContext, List<Review> reviews) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.review_list_item, parent, false);
        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        final Review current = reviews.get(position);
        holder.reviewTextTV.setText(current.getContent());
        holder.reviewAuthorTV.setText(current.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewTextTV, reviewAuthorTV;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewTextTV = itemView.findViewById(R.id.review_text_tv);
            reviewAuthorTV = itemView.findViewById(R.id.review_author_tv);
        }
    }
}
