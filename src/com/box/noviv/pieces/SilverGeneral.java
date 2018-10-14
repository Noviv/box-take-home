package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;
import com.box.noviv.utils.Coordinate;

public class SilverGeneral extends GamePiece {
    public SilverGeneral(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public void promote() {
        promoted = true;
    }

    @Override
    public String getRepr() {
        if (isPromoted()) {
            return isUpperPiece() ? "+S" : "+s";
        } else {
            return isUpperPiece() ? "S" : "s";
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
        switch (to.y - from.y) {
            case 1:
                return Math.abs(to.x - from.x) <= 1;
            case -1:
                return Math.abs(to.x - from.x) == 1;
            default:
                return false;
        }
    }
}
