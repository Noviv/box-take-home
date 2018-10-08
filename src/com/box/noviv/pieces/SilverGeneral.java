package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public class SilverGeneral extends GamePiece {
    public SilverGeneral(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public boolean move(String move, Minishogi game) {
        return true;
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "S" : "s";
    }
}
