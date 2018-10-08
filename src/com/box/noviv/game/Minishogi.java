package com.box.noviv.game;

import com.box.noviv.Utils;
import com.box.noviv.Utils.*;
import com.box.noviv.pieces.*;

public class Minishogi {
    private TestCase tc;
    private Board board = new Board();
    private PieceManager pm = new PieceManager();
    private boolean gameRunning = true;

    private boolean upperTurn = false;

    public Minishogi(String filePath) {
        try {
            tc = Utils.parseTestCase(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (InitialPosition ip : tc.initialPieces) {
            board.set(ip.position, pm.get(ip.piece));
        }

        for (String c : tc.upperCaptures) {
        }
        for (String c : tc.lowerCaptures) {
        }

        for (String s : tc.moves) {
            makeMove(s);
            if (!isRunning()) {
                System.out.println("illegal move");
                return;
            }
        }
    }

    public boolean isRunning() {
        return gameRunning;
    }

    public void prompt() {
        System.out.println(Utils.stringifyBoard(board.getBoardData()));
        System.out.println("Captures UPPER: []");
        System.out.println("Captures lower: []");
        System.out.println();
        System.out.print((upperTurn ? "UPPER" : "lower") + ">");
    }

    public void makeMove(String... cmd) {
        if (cmd[0].equals("move")) {
            System.out.println((upperTurn ? "upper" : "lower") + " player action: move");
        }

        upperTurn = !upperTurn;
    }

    private class Board {
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

        public void set(int x, int y, GamePiece v) {
            board[y][x] = v;
        }

        public void set(String pos, GamePiece v) {
            int x = convert(pos.charAt(1));
            int y = convert(pos.charAt(0));
            set(x, y, v);
        }

        public GamePiece get(int x, int y) {
            return board[y][x];
        }

        public GamePiece get(String pos) {
            int x = convert(pos.charAt(1));
            int y = convert(pos.charAt(0));
            return get(x, y);
        }

        public String[][] getBoardData() {
            String[][] ret = new String[5][5];

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    GamePiece piece = get(x, y);
                    if (piece != null) {
                        ret[y][x] = piece.getRepr();
                    } else {
                        ret[y][x] = " ";
                    }
                }
            }

            return ret;
        }
    }
}
