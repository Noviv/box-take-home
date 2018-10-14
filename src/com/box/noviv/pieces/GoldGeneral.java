package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class GoldGeneral extends GamePiece {
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
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);
        return checkValidMove(from, to, isUpperPiece());
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, boolean upper) {
        int dy = to.y - from.y;
        if (dy == (upper ? -1 : 1)) {
            return Math.abs(to.x - from.x) <= 1;
        } else if (dy == 0) {
            return Math.abs(to.x - from.x) == 1;
        } else if (dy == (upper ? 1 : -1)) {
            return to.x == from.x - 1;
        }

        return false;
    }
}