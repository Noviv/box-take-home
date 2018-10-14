package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class Bishop extends GamePiece {
    @Override
    public void setPromoted(boolean p) {
        promoted = p;
    }

    @Override
    public String getRepr() {
        if (isPromoted()) {
            return isUpperPiece() ? "+B" : "+b";
        } else {
            return isUpperPiece() ? "B" : "b";
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
        int dx = Math.abs(to.x - from.x);
        int dy = Math.abs(to.y - from.y);

        return dx == dy;
    }
}
