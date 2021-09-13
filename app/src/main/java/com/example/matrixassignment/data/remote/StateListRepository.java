package com.example.matrixassignment.data.remote;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.matrixassignment.data.models.State;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateListRepository {
    private static StateListRepository mInstance;
    private final ArrayList<State> mStatesList;

    public static StateListRepository getInstance() {
        if (mInstance == null) {
            mInstance = new StateListRepository();
        }
        return mInstance;
    }

    public void init(RequestQueue requestQueue) {
        fetchData(requestQueue);
    }

    private void fetchData(RequestQueue requestQueue) {
        String url = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;area;borders;alpha3Code;alpha2Code";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response ->
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            State[] states = gson.fromJson(response.toString(), State[].class);
            Collections.addAll(mStatesList, states);
        }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    private StateListRepository() {
        mStatesList = new ArrayList<>();
    }

    public ArrayList<State> getAllStates() {
        return this.mStatesList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public State findOne(String stateCode) {
        return mStatesList.stream().filter(state -> state.getAlpha3Code().equals(stateCode)).findFirst().orElse(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<State> findMany(String stateCode) {
        List<State> borderStates = new ArrayList<>();
        State state = findOne(stateCode);
        for (String borderStateCode : state.getBorders()) {
            borderStates.add(findOne(borderStateCode));
        }
        return borderStates;
    }

    public void sortByAreaDescending() {
        Collections.sort(mStatesList, (l1, l2) -> Double.compare(l2.getArea(), l1.getArea()));
    }

    public void sortByAreaAscending() {
        Collections.sort(mStatesList, (l1, l2) -> Double.compare(l1.getArea(), l2.getArea()));
    }

    public void sortByNameDescending() {
        Collections.sort(mStatesList, (l1, l2) -> l2.getName().compareTo(l1.getName()));
    }

    public void sortByNameAscending() {
        Collections.sort(mStatesList, (l1, l2) -> l1.getName().compareTo(l2.getName()));
    }
}