package com.box.noviv.pieces;

import com.box.noviv.game.Board;

public abstract class GamePiece {
    private boolean upper;
    protected boolean promoted = false;

    public final void setUpperPiece(boolean u) {
        upper = u;
    }

    public final boolean isUpperPiece() {
        return upper;
    }

    public final boolean isPromoted() {
        return promoted;
    }

    public abstract void setPromoted(boolean p);

    public abstract String getRepr();

    public abstract boolean validMove(String src, String dst, Board b);

    @Override
    public String toString() {
        return getRepr();
    }
}