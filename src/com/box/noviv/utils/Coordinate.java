package com.box.noviv.utils;

public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int _x, int _y) {
        x = _x;
        y = _y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
