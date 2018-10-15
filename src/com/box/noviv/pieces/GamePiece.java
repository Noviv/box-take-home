package com.box.noviv.pieces;

import com.box.noviv.game.Board;

public abstract class GamePiece {
    protected boolean promoted = false;
    private boolean upper;

    public final boolean isUpperPiece() {
        return upper;
    }

    public final void setUpperPiece(boolean u) {
        upper = u;
    }

    public final boolean isPromoted() {
        return promoted;
    }

    public abstract void setPromoted(boolean p);

    /**
     * @return Board-level representation of piece.
     */
    public abstract String getRepr();

    /**
     * @param src String containing source coordinate.
     * @param dst String containing destination coordinate.
     * @param b   Board currently working within.
     * @return True means this GamePiece is able to move from src to dst.
     */
    public abstract boolean validMove(String src, String dst, Board b);

    @Override
    public String toString() {
        return getRepr();
    }
}