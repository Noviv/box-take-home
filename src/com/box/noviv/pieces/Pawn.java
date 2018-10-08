package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public class Pawn extends GamePiece {
    public Pawn(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public boolean move(String move, Minishogi game) {
        return true;
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "P" : "p";
    }
}
