package com.udacity.popularmoviesstage2app.adpaters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.ui.DetailActivity;

import java.util.Collections;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Trailer> trailers = Collections.emptyList();

    public TrailerAdapter(Context mContext, List<Trailer> trailers) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.trailer_list_item, parent, false);
        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer current = trailers.get(position);

        String THUMBNAIL_URL = holder.itemView.getContext().getString(R.string.thumbnail_url);
        String THUMBNAIL_SUFFIX = holder.itemView.getContext().getString(R.string.thumbnail_suffix);
        String url = TextUtils.concat(THUMBNAIL_URL,current.getKey(),THUMBNAIL_SUFFIX).toString();

        Picasso.get()
                .load(url)
                .fit()
                .error(R.drawable.ic_videocam_black_48dp)
                .placeholder(R.drawable.ic_videocam_black_48dp)
                .into(holder.movieThumbnailIV);

//        holder.itemView.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext, DetailActivity.class);
//                        intent.putExtra("movie", current);
//                        mContext.startActivity(intent);
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        ImageView movieThumbnailIV;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumbnailIV = itemView.findViewById(R.id.trailer_poster_iv);
        }
    }
}
