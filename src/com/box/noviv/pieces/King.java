package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class King extends GamePiece {
    @Override
    public void setPromoted(boolean p) {
        if (p) {
            throw new IllegalStateException("cannot setPromoted King");
        }
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "K" : "k";
    }

    @Override
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);
        return checkValidMove(from, to);
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to) {
        int dx = Math.abs(to.x - from.x);
        int dy = Math.abs(to.y - from.y);

        return dx == 1 && dy == 1;
    }
}
