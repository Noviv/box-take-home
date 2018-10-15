package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class GoldGeneral extends GamePiece {
    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        int dy = to.vert - from.vert;
        if (dy == (upper ? -1 : 1)) {
            return Math.abs(to.horiz - from.horiz) <= 1;
        } else if (dy == 0) {
            return Math.abs(to.horiz - from.horiz) == 1;
        } else if (dy == (upper ? 1 : -1)) {
            return to.horiz == from.horiz;
        }

        return false;
    }

    @Override
    public void setPromoted(boolean p) {
        if (p) {
            throw new IllegalStateException("cannot setPromoted a GoldGeneral");
        }
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "G" : "g";
    }

    @Override
    public boolean validMove(String src, String dst, Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);
        return checkValidMove(from, to, b, isUpperPiece());
    }
}