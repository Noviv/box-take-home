package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
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
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            return GoldGeneral.checkValidMove(from, to, isUpperPiece());
        } else {
            return checkValidMove(from, to, isUpperPiece());
        }
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, boolean upper) {
        int dy = to.y - from.y;
        if (dy == (upper ? -1 : 1)) {
            return Math.abs(to.x - from.x) <= 1;
        } else if (dy == (upper ? 1 : -1)) {
            return Math.abs(to.x - from.x) == 1;
        }

        return false;
    }
}
