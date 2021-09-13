package com.example.matrixassignment.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.matrixassignment.R;
import com.example.matrixassignment.view.adapters.IStateSelectionObserver;
import com.example.matrixassignment.data.remote.StateListRepository;
import com.example.matrixassignment.view.adapters.StatesAdapter;

public class StateListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private StatesAdapter mAdapter;
    private Spinner stateNameSpinner;
    private Spinner stateAreaSpinner;

    public StateListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        stateNameSpinner = view.findViewById(R.id.nameSpinner);
        stateAreaSpinner = view.findViewById(R.id.areaSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.data_array, android.R.layout.simple_spinner_item);
        stateNameSpinner.setAdapter(spinnerAdapter);
        stateAreaSpinner.setAdapter(spinnerAdapter);
        stateNameSpinner.setOnItemSelectedListener(this);
        stateAreaSpinner.setOnItemSelectedListener(this);
        RecyclerView stateRecyclerView = view.findViewById(R.id.statesRecyclerView);
        mAdapter = new StatesAdapter((IStateSelectionObserver) getActivity(), getContext(), StateListRepository.getInstance().getAllStates());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;

    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.nameSpinner:
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        StateListRepository.getInstance().sortByNameAscending();
                        stateAreaSpinner.setSelection(0);
                        break;
                    case 2:
                        StateListRepository.getInstance().sortByNameDescending();
                        stateAreaSpinner.setSelection(0);
                        break;
                }
                break;
            case R.id.areaSpinner:
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        StateListRepository.getInstance().sortByAreaAscending();
                        stateNameSpinner.setSelection(0);
                        break;
                    case 2:
                        StateListRepository.getInstance().sortByAreaDescending();
                        stateNameSpinner.setSelection(0);
                        break;
                }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}