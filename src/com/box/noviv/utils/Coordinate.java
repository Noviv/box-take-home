package com.box.noviv.utils;

public final class Coordinate {
    public final int horiz;
    public final int vert;

    public Coordinate(int _x, int _y) {
        horiz = _x;
        vert = _y;
    }

    public String getRepr() {
        return revert(horiz, true) + "" + revert(vert, false);
    }

    @Override
    public String toString() {
        return "(" + horiz + ", " + vert + ")";
    }

    private char revert(int i, boolean alpha) {
        if (0 <= i && i <= 5) {
            return (char) ((alpha ? 'a' : '1') + i);
        } else {
            throw new IllegalArgumentException("invalid position reversion: " + i);
        }
    }
}
