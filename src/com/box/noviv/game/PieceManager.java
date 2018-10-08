package com.box.noviv.game;

import com.box.noviv.pieces.*;

public final class PieceManager {
    private class Player {
        public final boolean isUpper;

        private Pawn pawn = null;
        private SilverGeneral sgeneral = null;
        private GoldGeneral ggeneral = null;
        private Bishop bishop = null;
        private Rook rook = null;
        private King king = null;

        public Player(boolean upper) {
            isUpper = upper;
        }
    }

    private Player upper = new Player(true);
    private Player lower = new Player(false);

    private King KING(Player p) {
        if (p.king != null) {
            throw new IllegalStateException("can't create multiple Kings");
        }

        return p.king = new King(p.isUpper);
    }

    private Rook ROOK(Player p) {
        if (p.rook != null) {
            throw new IllegalStateException("can't create multiple Rooks");
        }

        return p.rook = new Rook(p.isUpper);
    }

    private Bishop BISHOP(Player p) {
        if (p.bishop != null) {
            throw new IllegalStateException("can't create multiple Bishops");
        }

        return p.bishop = new Bishop(p.isUpper);
    }

    private GoldGeneral GGENERAL(Player p) {
        if (p.ggeneral != null) {
            throw new IllegalStateException("can't create multiple GoldGenerals");
        }

        return p.ggeneral = new GoldGeneral(p.isUpper);
    }

    private SilverGeneral SGENERAL(Player p) {
        if (p.sgeneral != null) {
            throw new IllegalStateException("can't create multiple SilverGenerals");
        }

        return p.sgeneral = new SilverGeneral(p.isUpper);
    }

    private Pawn PAWN(Player p) {
        if (p.pawn != null) {
            throw new IllegalStateException("can't create multiple Pawns");
        }

        return p.pawn = new Pawn(p.isUpper);
    }


    public GamePiece get(String piece) {
        String pieceLower = piece.toLowerCase();
        Player p = pieceLower.equals(piece) ? lower : upper;

        switch (pieceLower.charAt(0)) { // DOES NOT HANDLE PROMOTED
            case 'k':
                return KING(p);
            case 'r':
                return ROOK(p);
            case 'b':
                return BISHOP(p);
            case 'g':
                return GGENERAL(p);
            case 's':
                return SGENERAL(p);
            case 'p':
                return PAWN(p);
        }


        return null;
    }
}
