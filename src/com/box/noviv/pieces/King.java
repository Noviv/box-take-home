package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class King extends GamePiece {
    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        int dx = Math.abs(to.horiz - from.horiz);
        int dy = Math.abs(to.vert - from.vert);

        return dx <= 1 && dy <= 1;
    }

    @Override
    public void setPromoted(boolean p) {
        if (p) {
            throw new IllegalStateException("cannot setPromoted King");
        }
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "K" : "k";
    }

    @Override
    public boolean validMove(String src, String dst, Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);
        return checkValidMove(from, to, b, isUpperPiece());
    }
}
