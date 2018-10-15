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
            if (King.checkValidMove(from, to, b, isUpperPiece())) {
                return true;
            }
        }

        return checkValidMove(from, to, b, isUpperPiece());
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to, Board b, boolean upper) {
        if (b.get(to) != null && b.get(to).isUpperPiece() == upper) {
            return false;
        }

        int dx = Math.abs(from.horiz - to.horiz);
        int dy = Math.abs(from.vert - to.vert);

        if (dx != dy || dx == 0) {
            return false;
        }

        int x = from.horiz;
        int y = from.vert;

        if (to.horiz > from.horiz) {
            x++;
        } else {
            x--;
        }
        if (to.vert > from.vert) {
            y++;
        } else {
            y--;
        }

        while (x != to.horiz && y != to.vert) {
            if (b.get(x, y) != null) {
                return false;
            }
            if (to.horiz > from.horiz) {
                x++;
            } else {
                x--;
            }
            if (to.vert > from.vert) {
                y++;
            } else {
                y--;
            }
        }

        return true;
    }
}
