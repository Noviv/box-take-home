package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class Pawn extends GamePiece {

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
            return GoldGeneral.checkValidMove(from, to, isUpperPiece());
        } else {
            return checkValidMove(from, to, isUpperPiece());
        }
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, boolean upper) {
        if (from.x != to.x) { // cannot move horizontal
            return false;
        }

        if (from.y + (upper ? -1 : 1) != to.y) { // pawn must move vertical
            return false;
        }

        return true;
    }
}
