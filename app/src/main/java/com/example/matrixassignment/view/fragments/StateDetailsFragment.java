package com.example.matrixassignment.view.fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matrixassignment.R;
import com.example.matrixassignment.data.models.State;
import com.example.matrixassignment.data.remote.StateListSingleton;
import com.example.matrixassignment.view.adapters.StatesAdapter;

import java.util.ArrayList;
import java.util.List;

public class StateDetailsFragment extends Fragment {
    private State mState;
    private final List<State> mBorderStates;

    public StateDetailsFragment() {
        mBorderStates = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mState = (State) bundle.getSerializable("State");
        }
        for (String codeState: mState.getBorders()) {
            mBorderStates.add(StateListSingleton.getInstance().getArray().stream().filter(state -> state.getAlpha3Code().equals(codeState)).findFirst().orElse(null));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_details, container, false);
        RecyclerView stateRecyclerView = view.findViewById(R.id.statesRecyclerView);
        TextView titleTextview = view.findViewById(R.id.titleTextView);
        titleTextview.setText(mState.getName().concat(" borders:"));
        StatesAdapter mAdapter = new StatesAdapter(getActivity(), getContext(), mBorderStates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}