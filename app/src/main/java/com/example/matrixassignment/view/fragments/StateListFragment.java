package com.example.matrixassignment.view.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.matrixassignment.R;
import com.example.matrixassignment.data.remote.StateListSingleton;
import com.example.matrixassignment.data.remote.VolleySingleton;
import com.example.matrixassignment.data.models.State;
import com.example.matrixassignment.view.adapters.StatesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StateListFragment extends Fragment {
    private final ArrayList<State> mAllStateList;
    private RequestQueue mRequestQueue;
    private StatesAdapter mAdapter;

    public StateListFragment() {
        mAllStateList = StateListSingleton.getInstance().getArray();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        fetchData();
        Spinner stateNameSpinner = view.findViewById(R.id.nameSpinner);
        Spinner stateAreaSpinner = view.findViewById(R.id.areaSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.data_array, android.R.layout.simple_spinner_item);
        stateNameSpinner.setAdapter(adapter);
        stateAreaSpinner.setAdapter(adapter);

        stateNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    sortByNameAscending();
                } else if (position == 2) {
                    sortByNameDescending();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stateAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    sortByAreaAscending();
                } else if (position == 2) {
                    sortByAreaDescending();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RecyclerView stateRecyclerView = view.findViewById(R.id.statesRecyclerView);
        mAdapter = new StatesAdapter(getActivity(), getContext(), mAllStateList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void sortByAreaDescending() {
        Collections.sort(mAllStateList, (l1, l2) -> Double.compare(l2.getArea(), l1.getArea()));
    }

    private void sortByAreaAscending() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(mAllStateList, Comparator.comparingDouble(State::getArea));
        }
    }

    private void sortByNameDescending() {
        Collections.sort(mAllStateList, (l1, l2) -> l2.getName().compareTo(l1.getName()));
    }

    private void sortByNameAscending() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(mAllStateList, Comparator.comparing(State::getName));
        }
    }

    private void fetchData() {
        String url = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;flag;area;borders;alpha3Code;alpha2Code";
        mAllStateList.clear();

        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response ->
        {
            for (int i = 0; i < response.length(); i++) {
                try {
                    double stateArea;
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    String stateName = jsonObject.getString("name");
                    String stateNativeName = jsonObject.getString("nativeName");
                    String stateFlag = jsonObject.getString("flag");
                    String state3Code = jsonObject.getString("alpha3Code");
                    String state2Code = jsonObject.getString("alpha2Code");
                    JSONArray stateBordersJSONArray = jsonObject.getJSONArray("borders");
                    String[] stateBorders = new String[stateBordersJSONArray.length()];
                    for (int j = 0; j < stateBorders.length; j++) {
                        stateBorders[j] = stateBordersJSONArray.optString(j);
                    }
                    try {
                        stateArea = jsonObject.getDouble("area");
                    } catch (JSONException e) {
                        stateArea = 0;
                    }
                    mAllStateList.add(new State(stateName, stateNativeName, stateArea, stateBorders, stateFlag, state3Code, state2Code));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(getContext(), "Error fetching states", Toast.LENGTH_SHORT).show());
        mRequestQueue.add(jsonObjectRequest);
    }
}