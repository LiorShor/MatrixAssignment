package com.example.matrixassignment;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {
    private final Context mContext;
    private final List<State> mStatesArray;
    private final FragmentActivity mFragmentActivity;

    public StatesAdapter(FragmentActivity iFragmentActivity,Context iContext, List<State> iStatesArrayList) {
        this.mStatesArray = iStatesArrayList;
        this.mContext = iContext;
        this.mFragmentActivity = iFragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatesAdapter.ViewHolder holder, int position) {
        holder.mStateNameTextView.setText(mStatesArray.get( holder.getAdapterPosition()).getName());
        holder.mStateNativeNameTextView.setText(mStatesArray.get( holder.getAdapterPosition()).getNativeName());
        holder.mStateAreaTextView.setText(String.valueOf(mStatesArray.get( holder.getAdapterPosition()).getArea()));
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<>());

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(mStatesArray.get( holder.getAdapterPosition()).getFlagUrl()))
                .into(holder.mStateFlagImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity = (MainActivity) mFragmentActivity;
                mainActivity.loadStateDetails(mStatesArray.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStatesArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mStateNameTextView;
        private final TextView mStateNativeNameTextView;
        private final TextView mStateAreaTextView;
        private final ImageView mStateFlagImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mStateNameTextView = itemView.findViewById(R.id.stateEnglishNameTextView);
            mStateNativeNameTextView = itemView.findViewById(R.id.stateNativeNameTextView);
            mStateAreaTextView = itemView.findViewById(R.id.stateAreaTextView);
            mStateFlagImageView = itemView.findViewById(R.id.stateFlagImageView);
        }

    }
}
