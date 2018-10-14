package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class Rook extends GamePiece {
    public Rook(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public void promote() {
        promoted = true;
    }

    @Override
    public String getRepr() {
        if (isPromoted()) {
            return isUpperPiece() ? "+R" : "+r";
        } else {
            return isUpperPiece() ? "R" : "r";
        }
    }

    @Override
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            if (King.checkValidMove(from, to)) {
                return true;
            }
        }

        return checkValidMove(from, to);
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to) {
        if (to.x != from.x) {
            return to.y == from.y;
        }

        if (to.y != from.y) {
            return to.x == from.x;
        }

        return false;
    }
}
