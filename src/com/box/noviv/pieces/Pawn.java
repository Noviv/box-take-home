package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class Pawn extends GamePiece {
    public Pawn(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public void promote() {
        promoted = true;
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
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            return GoldGeneral.checkValidMove(from, to);
        } else {
            return checkValidMove(from, to);
        }
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to) {
        if (from.x != to.x) { // cannot move horizontal
            return false;
        }

        if (from.y + 1 != to.y) { // pawn must move vertical
            return false;
        }

        return true;
    }
}
