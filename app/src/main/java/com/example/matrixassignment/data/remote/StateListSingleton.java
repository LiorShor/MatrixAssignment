package com.example.matrixassignment.data.remote;

import com.example.matrixassignment.data.models.State;

import java.util.ArrayList;

public class StateListSingleton {
    private static StateListSingleton mInstance;
    private final ArrayList<State> list;

    public static StateListSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new StateListSingleton();
        }
        return mInstance;
    }

    private StateListSingleton() {
        list = new ArrayList<>();
    }

    // retrieve array from anywhere
    public ArrayList<State> getArray() {
        return this.list;
    }
}