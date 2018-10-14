package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class GoldGeneral extends GamePiece {
    public GoldGeneral(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public void promote() {
        throw new IllegalStateException("cannot promote a GoldGeneral");
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "G" : "g";
    }

    @Override
    public boolean validMove(String src, String dst, Minishogi.Board b) {
        Coordinate from = b.convert(src);
        Coordinate to = b.convert(dst);
        return checkValidMove(from, to);
    }

    public static boolean checkValidMove(Coordinate from, Coordinate to) {
        switch (to.y - from.y) {
            case 1:
                return Math.abs(to.x - from.x) <= 1;
            case 0:
                return Math.abs(to.x - from.x) == 1;
            case -1:
                return to.x == from.x - 1;
            default:
                return false;
        }
    }
}