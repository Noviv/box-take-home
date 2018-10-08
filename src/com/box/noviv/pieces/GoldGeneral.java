package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public class GoldGeneral extends GamePiece {
    public GoldGeneral(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public boolean move(String move, Minishogi game) {
        return true;
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "G" : "g";
    }
}
