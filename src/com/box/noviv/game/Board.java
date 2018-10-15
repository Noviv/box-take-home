package com.box.noviv.game;

import com.box.noviv.pieces.GamePiece;
import com.box.noviv.pieces.King;
import com.box.noviv.utils.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private GamePiece[][] board;

    public Board() {
        board = new GamePiece[5][5];
        for (char x = 0; x < 5; x++) {
            for (char y = 0; y < 5; y++) {
                set(x, y, null);
            }
        }
    }

    private int convert(char c) {
        if ('1' <= c && c <= '5') {
            return c - '1';
        } else if ('a' <= c && c <= 'e') {
            return c - 'a';
        } else if ('A' <= c && c <= 'E') {
            return c - 'E';
        } else {
            throw new IllegalArgumentException("invalid position: " + c);
        }
    }

    public Coordinate convert(String pos) {
        return new Coordinate(convert(pos.charAt(0)), convert(pos.charAt(1)));
    }

    public void set(int x, int y, GamePiece v) {
        board[y][x] = v;
    }

    public void set(Coordinate c, GamePiece v) {
        board[c.y][c.x] = v;
    }

    public void set(String pos, GamePiece v) {
        set(convert(pos), v);
    }

    public GamePiece get(int x, int y) {
        return board[y][x];
    }

    public GamePiece get(Coordinate c) {
        return board[c.y][c.x];
    }

    public GamePiece get(String pos) {
        return get(convert(pos));
    }

    public String[][] getBoardData() {
        String[][] ret = new String[5][5];

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                GamePiece piece = get(x, y);
                if (piece != null) {
                    ret[x][y] = piece.getRepr();
                } else {
                    ret[x][y] = "__";
                }
            }
        }

        return ret;
    }

    public void printCheckStatus(boolean upper) {
        ArrayList<GamePiece> attackers = new ArrayList<>();
        ArrayList<Coordinate> attackersC = new ArrayList<>();
        Coordinate kingC = null;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                GamePiece gp = get(r, c);
                if (gp != null) {
                    if (gp instanceof King && gp.isUpperPiece() == upper) {
                        kingC = new Coordinate(r, c);
                    } else if (gp.isUpperPiece() != upper) {
                        attackers.add(gp);
                        attackersC.add(new Coordinate(r, c));
                    }
                }
            }
        }

        assert kingC != null : "could not find king";

        boolean inCheck = false;

        ArrayList<String> moves = new ArrayList<>();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                Coordinate newC = new Coordinate(kingC.x + r, kingC.y + c);
                if (newC.x < 0 || newC.x > 4 || newC.y < 0 || newC.y > 4) {
                    continue;
                }

                GamePiece gp = get(newC);
                boolean unblocked = true;
                for (int i = 0; i < attackers.size(); i++) {
                    if (attackers.get(i).validMove(attackersC.get(i).getRepr(), newC.getRepr(), this)) {
                        if (r == 0 && c == 0) {
                            inCheck = true;
                            System.out.println(attackers.get(i) + " can hit from " + attackersC.get(i).getRepr() + " to " + newC.getRepr());
                        }
                        unblocked = false;
                    }
                }
                if (gp != null && r != 0 && c != 0 && gp.isUpperPiece() == upper) {
                    unblocked = false;
                }
                if (unblocked) {
                    moves.add("move " + kingC.getRepr() + " " + newC.getRepr());
                }
            }
        }

        if (inCheck) {
            Collections.sort(moves);
            System.out.println((upper ? "UPPER" : "lower") + " player is in check!");
            System.out.println("Available moves:");
            for (String move : moves) {
                System.out.println(move);
            }
        }
    }
}
