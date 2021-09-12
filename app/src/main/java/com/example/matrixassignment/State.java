package com.example.matrixassignment;

import java.io.Serializable;

public class State implements Serializable {
    private final String name;
    private final String nativeName;
    private final double area;
    private final String[] borders;
    private final String flagUrl;

    public State(String name, String nativeName, double area, String[] borders, String flagURL) {
        this.name = name;
        this.nativeName = nativeName;
        this.area = area;
        this.borders = borders;
        this.flagUrl = flagURL;
    }

    public String getName() {
        return name;
    }

    public String getFlagUrl() {
        return flagUrl;
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
}
