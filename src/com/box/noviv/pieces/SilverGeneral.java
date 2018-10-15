package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class SilverGeneral extends GamePiece {
    @Override
    public void setPromoted(boolean p) {
        promoted = p;
    }

    @Override
    public String getRepr() {
        if (isPromoted()) {
            return isUpperPiece() ? "+S" : "+s";
        } else {
            return isUpperPiece() ? "S" : "s";
        }
    }

    @Override
    public boolean validMove(String src, String dst, Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            return GoldGeneral.checkValidMove(from, to, b, isUpperPiece());
        } else {
            return checkValidMove(from, to, b, isUpperPiece());
        }
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        int dy = to.vert - from.vert;
        if (dy == (upper ? -1 : 1)) {
            return Math.abs(to.horiz - from.horiz) <= 1;
        } else if (dy == (upper ? 1 : -1)) {
            return Math.abs(to.horiz - from.horiz) == 1;
        }

        return false;
    }
}
