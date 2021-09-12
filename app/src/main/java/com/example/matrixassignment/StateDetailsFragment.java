package com.example.matrixassignment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class StateDetailsFragment extends Fragment {
    private State mState;
    private List<State> mStatesArrayList;
    private final List<State> mBorderStates;

    public StateDetailsFragment() {
        mStatesArrayList = new ArrayList<>();
        mBorderStates = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mState = (State) bundle.getSerializable("State");
            mStatesArrayList = (ArrayList<State>) bundle.getSerializable("StatesList");
        }
        for (String codeState: mState.getBorders()) {
            mBorderStates.add(mStatesArrayList.stream().filter(state -> state.getCode().equals(codeState)).findFirst().orElse(null));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_state_details, container, false);
        RecyclerView stateRecyclerView = view.findViewById(R.id.statesRecyclerView);
        StatesAdapter mAdapter = new StatesAdapter(getActivity(), getContext(), mBorderStates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}