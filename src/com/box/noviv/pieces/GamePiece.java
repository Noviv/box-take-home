package com.box.noviv.pieces;

import com.box.noviv.game.Minishogi;

public abstract class GamePiece {
    private boolean upper;
    protected boolean promoted = false;

    public GamePiece(boolean isUpper) {
        upper = isUpper;
    }

    public final boolean isUpperPiece() {
        return upper;
    }

    public final boolean isPromoted() { return promoted; }

    public abstract void promote();

    public abstract String getRepr();

    public abstract boolean validMove(String src, String dst, Minishogi.Board b);

    @Override
    public String toString() {
        return getRepr();
    }
}