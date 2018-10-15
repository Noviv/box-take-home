package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class Pawn extends GamePiece {

    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        if (from.horiz != to.horiz) { // cannot move horizontal
            return false;
        }

        if (from.vert + (upper ? -1 : 1) != to.vert) { // pawn must move vertical
            return false;
        }

        return true;
    }

    @Override
    public void setPromoted(boolean p) {
        promoted = p;
    }

    @Override
    public String getRepr() {
        if (isPromoted()) {
            return isUpperPiece() ? "+P" : "+p";
        } else {
            return isUpperPiece() ? "P" : "p";
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
}
