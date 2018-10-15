package com.box.noviv.pieces;

import com.box.noviv.game.Board;
import com.box.noviv.utils.Coordinate;

public class Rook extends GamePiece {
    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        if (to.vert != from.vert && to.horiz != from.horiz) {
            return false;
        } else if (to.vert == from.vert && to.horiz == from.horiz) {
            return false;
        }

        boolean vertical = to.vert != from.vert;
        int d = vertical ? to.vert - from.vert : to.horiz - from.horiz;

        int dofs = d > 0 ? -1 : 1;

        d += dofs;

        while (d != 0) {
            if (vertical) {
                if (b.get(from.horiz, from.vert + d) != null) {
                    return false;
                }
            } else {
                if (b.get(from.horiz + d, from.vert) != null) {
                    return false;
                }
            }
            d += dofs;
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
            return isUpperPiece() ? "+R" : "+r";
        } else {
            return isUpperPiece() ? "R" : "r";
        }
    }

    @Override
    public boolean validMove(String src, String dst, Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            if (King.checkValidMove(from, to, b, isUpperPiece())) {
                return true;
            }
        }

        return checkValidMove(from, to, b, isUpperPiece());
    }
}
