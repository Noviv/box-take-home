package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public class Rook extends GamePiece {
    public Rook(boolean isUpper) {
        super(isUpper);
    }

    @Override
    public boolean move(String move, Minishogi game) {
        return true;
    }

    @Override
    public String getRepr() {
        return isUpperPiece() ? "R" : "r";
    }
}
