package com.box.noviv.game;

import com.box.noviv.utils.Coordinate;
import com.box.noviv.utils.Utils;
import com.box.noviv.utils.Utils.*;
import com.box.noviv.pieces.*;

import java.util.ArrayList;

public class Minishogi {
    private TestCase tc;
    private Board board = new Board();
    private PieceManager pm = new PieceManager();
    private boolean gameRunning = true;

    private boolean upperTurn = false;

    private ArrayList<GamePiece> upperCaptures = new ArrayList<>();
    private ArrayList<GamePiece> lowerCaptures = new ArrayList<>();

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
            if (!c.isEmpty()) {
                System.out.println("cap: " + c);
                upperCaptures.add(pm.get(c));
            }
        }
        for (String c : tc.lowerCaptures) {
            if (!c.isEmpty()) {
                System.out.println("cap: " + c);
                lowerCaptures.add(pm.get(c));
            }
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
        System.out.println("Captures UPPER: " + upperCaptures);
        System.out.println("Captures lower: " + lowerCaptures);
        System.out.println();
        System.out.print((upperTurn ? "UPPER" : "lower") + ">");
    }

    public void makeMove(String... cmd) {
        if (cmd[0].equals("move")) {
            System.out.println((upperTurn ? "upper" : "lower") + " player action: move");

            GamePiece src = board.get(cmd[1]);
            GamePiece dst = board.get(cmd[2]);

            if (src.isUpperPiece() != upperTurn) {
                System.out.println("INVALID MOVE: piece not owned");
                gameRunning = false;
                return;
            }

            if (!src.validMove(cmd[1], cmd[2], board)) {
                System.out.println("INVALID MOVE: can't make that move");
                gameRunning = false;
                return;
            }

            if (dst != null && dst.isUpperPiece() == upperTurn) {
                System.out.println("INVALID MOVE: taking piece that is owned");
                gameRunning = false;
                return;
            }

            board.set(cmd[2], src);
            board.set(cmd[1], null);
        }

        upperTurn = !upperTurn;
    }

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
                        ret[x][y] = " ";
                    }
                }
            }

            return ret;
        }
    }
}
