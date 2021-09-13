package com.example.matrixassignment.data.remote;

import com.example.matrixassignment.data.models.State;

import java.util.ArrayList;

public class StateListSingleton {
    private static StateListSingleton mInstance;
    private ArrayList<State> list = null;

    public static StateListSingleton getInstance() {
        if(mInstance == null)
            mInstance = new StateListSingleton();

        return mInstance;
    }

    private StateListSingleton() {
        list = new ArrayList<State>();
    }
    // retrieve array from anywhere
    public ArrayList<State> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(State value) {
        list.add(value);
    }
}