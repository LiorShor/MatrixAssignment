package com.example.matrixassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {
    private final Context mContext;
    private final List<State> mStatesArray;
    private final FragmentActivity mFragmentActivity;

    public StatesAdapter(FragmentActivity iFragmentActivity, Context iContext, List<State> iStatesArrayList) {
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
        State state = mStatesArray.get(holder.getAdapterPosition());
        holder.mStateNameTextView.setText(state.getName());
        holder.mStateNativeNameTextView.setText(state.getNativeName());
        holder.mStateAreaTextView.setText(String.valueOf(state.getArea()));
        String COUNTRIES_FLAGS_URL = "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png250px/";
        Picasso.get().load(COUNTRIES_FLAGS_URL + state.getAlpha2Code().toLowerCase() + ".png").resize(90, 90).centerCrop().into(holder.mStateFlagImageView);
        holder.itemView.setOnClickListener(v -> {
            if (mStatesArray.get(holder.getAdapterPosition()).getBorders().length > 0) {
                MainActivity mainActivity = (MainActivity) mFragmentActivity;
                mainActivity.loadStateDetails(mStatesArray.get(holder.getAdapterPosition()), mStatesArray);
            } else {
                Toast.makeText(mContext, "Country without borders", Toast.LENGTH_SHORT).show();
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
