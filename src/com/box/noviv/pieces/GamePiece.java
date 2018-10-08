package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public abstract class GamePiece {
    private boolean upper;

    public GamePiece(boolean isUpper) {
        upper = isUpper;
    }

    public boolean isUpperPiece() {
        return upper;
    }

    public abstract boolean move(String move, Minishogi game);

    public abstract String getRepr();
}
