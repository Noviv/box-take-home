package com.box.noviv.pieces;

import com.box.noviv.game.Board;
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
    public boolean validMove(String src, String dst, Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);

        if (isPromoted()) {
            if (King.checkValidMove(from, to)) {
                return true;
            }
        }

        return checkValidMove(from, to, b);
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b) {
        int dx = to.x - from.x;
        int dy = to.y - from.y;

        if (Math.abs(dx) != Math.abs(dy)) {
            return false;
        }

        int dxofs = dx > 0 ? -1 : 1;
        int dyofs = dy > 0 ? -1 : 1;

        dx += dxofs;
        dy += dyofs;

        while (Math.abs(dx) != 1 && Math.abs(dy) != 1) {
            if (b.get(from.x + dx, from.y + dy) != null) {
                return false;
            }

            dx += dxofs;
            dy += dyofs;
        }

        return true;
    }
}
