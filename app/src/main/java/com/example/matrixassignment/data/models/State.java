package com.example.matrixassignment.data.models;

import java.io.Serializable;

public class State implements Serializable {
    private final String name;
    private final String nativeName;
    private final double area;
    private final String[] borders;
    private final String alpha2Code;
    private final String alpha3Code;

    public State(String name, String nativeName, double area, String[] borders, String alpha3Code, String alpha2Code) {
        this.name = name;
        this.nativeName = nativeName;
        this.area = area;
        this.borders = borders;
        this.alpha3Code = alpha3Code;
        this.alpha2Code = alpha2Code;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public double getArea() {
        return area;
    }

    public String[] getBorders() {
        return borders;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }
}
