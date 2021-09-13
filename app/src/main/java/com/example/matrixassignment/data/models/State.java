package com.example.matrixassignment.data.models;

import java.io.Serializable;

public class State implements Serializable {
    private final String mName;
    private final String mNativeName;
    private final double mArea;
    private final String[] mBorders;
    private final String mFlagURL;
    private final String mAlpha3Code;
    private final String mAlpha2Code;

    public State(String name, String nativeName, double area, String[] borders, String flagURL, String alpha3Code, String alpha2Code) {
        this.mName = name;
        this.mNativeName = nativeName;
        this.mArea = area;
        this.mBorders = borders;
        this.mFlagURL = flagURL;
        this.mAlpha3Code = alpha3Code;
        this.mAlpha2Code = alpha2Code;
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

    public String getAlpha3Code() {
        return mAlpha3Code;
    }

    public String getAlpha2Code() {
        return mAlpha2Code;
    }
}
