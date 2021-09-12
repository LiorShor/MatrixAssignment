package com.example.matrixassignment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateListFragment extends Fragment {
    private final List<State> mStatesArrayList;
    private RequestQueue mRequestQueue;
    private StatesAdapter mAdapter;

    public StateListFragment() {
        mStatesArrayList = new ArrayList<>();
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
        mAdapter = new StatesAdapter(getActivity(), getContext(), mStatesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void sortByAreaDescending() {
        Collections.sort(mStatesArrayList, (l1, l2) -> Double.compare(l2.getArea(), l1.getArea()));
    }

    private void sortByAreaAscending() {
        Collections.sort(mStatesArrayList, (l1, l2) -> Double.compare(l1.getArea(), l2.getArea()));
    }

    private void sortByNameDescending() {
        Collections.sort(mStatesArrayList, (l1, l2) -> l2.getName().compareTo(l1.getName()));
    }

    private void sortByNameAscending() {
        Collections.sort(mStatesArrayList, (l1, l2) -> l1.getName().compareTo(l2.getName()));
    }

    private void fetchData() {
        String url = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;flag;area;borders;alpha3Code";
        mStatesArrayList.clear();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response ->
        {
            for (int i = 0; i < response.length(); i++) {
                try {
                    double stateArea;
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    String stateName = jsonObject.getString("name");
                    String stateNativeName = jsonObject.getString("nativeName");
                    String stateFlag = jsonObject.getString("flag");
                    String stateCode = jsonObject.getString("alpha3Code");
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
                    mStatesArrayList.add(new State(stateName, stateNativeName, stateArea, stateBorders, stateFlag, stateCode));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(getContext(), "Error fetching states", Toast.LENGTH_SHORT).show());
        mRequestQueue.add(jsonObjectRequest);
    }
}