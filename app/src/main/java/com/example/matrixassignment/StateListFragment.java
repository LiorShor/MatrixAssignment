package com.example.matrixassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        fetchData();
        Spinner stateNameSpinner = view.findViewById(R.id.nameSpinner);
        Spinner stateAreaSpinner = view.findViewById(R.id.areaSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.data_array, android.R.layout.simple_spinner_item);
        stateNameSpinner.setAdapter(adapter);
        stateAreaSpinner.setAdapter(adapter);

        stateAreaSpinner.setOnItemClickListener((adapterView, view1, position, id) ->
        {
            if(adapterView.getItemAtPosition(position).equals("Top to bottom"))
            {
            }
        });
        RecyclerView stateRecyclerView = view.findViewById(R.id.statesRecyclerView);
        mAdapter = new StatesAdapter(getActivity(),getContext(), mStatesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stateRecyclerView.setAdapter(mAdapter);
        stateRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void fetchData() {
        String url = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;flag;area;borders";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response ->
        {

            for (int i = 0; i < response.length(); i++) {
                try {
                    double stateArea;
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    String stateName = jsonObject.getString("name");
                    String stateNativeName = jsonObject.getString("nativeName");
                    String stateFlag = jsonObject.getString("flag");
                    try {
                        stateArea = jsonObject.getDouble("area");
                    } catch (JSONException e) {
                        stateArea = 0;
                    }
                    mStatesArrayList.add(new State(stateName, stateNativeName, stateArea, null, stateFlag));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
//                String[] stateBorders = response.getJSONArray("borders");
//                Gson gson = new Gson();
//                String[] borders = gson.fromJson(response,)
            }
            mAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(getContext(), "Error fetching states", Toast.LENGTH_SHORT).show());
        mRequestQueue.add(jsonObjectRequest);
    }
}