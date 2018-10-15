package com.box.noviv.utils;

public final class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public String getRepr() {
        return revert(y, true) + "" + revert(x, false);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private char revert(int i, boolean alpha) {
        if (0 <= i && i <= 5) {
            return (char) ((alpha ? 'a' : '1') + i);
        } else {
            throw new IllegalArgumentException("invalid position reversion: " + i);
        }
    }
}
