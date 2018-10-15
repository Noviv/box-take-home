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

    /**
     * @param i     Integer in raw coodinate space.
     * @param alpha Axis determination.
     * @return Character represneting on element of one axis (determined by alpha).
     */
    private char revert(int i, boolean alpha) {
        if (0 <= i && i <= 5) {
            return (char) ((alpha ? 'a' : '1') + i);
        } else {
            throw new IllegalArgumentException("invalid position reversion: " + i);
        }
    }
}
