package com.example.matrixassignment;

import java.io.Serializable;

public class State implements Serializable {
    private final String mName;
    private final String mNativeName;
    private final double mArea;
    private final String[] mBorders;
    private final String mFlagURL;
    private final String mCode;

    public State(String name, String nativeName, double area, String[] borders, String flagURL,String code) {
        this.mName = name;
        this.mNativeName = nativeName;
        this.mArea = area;
        this.mBorders = borders;
        this.mFlagURL = flagURL;
        this.mCode = code;
    }

    public String getName() {
        return mName;
    }

    public String getFlagURL() {
        return mFlagURL;
    }

    public String getNativeName() {
        return mNativeName;
    }

    public double getArea() {
        return mArea;
    }

    public String[] getBorders() {
        return mBorders;
    }

    public String getCode() {
        return mCode;
    }
}
