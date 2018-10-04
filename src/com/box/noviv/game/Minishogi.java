package com.box.noviv.game;

import com.box.noviv.Utils;
import com.box.noviv.Utils.*;

public class Minishogi {
    private TestCase tc;
    private Board board = new Board();
    private boolean gameRunning = true;

    public Minishogi(String filePath) {
        try {
            tc = Utils.parseTestCase(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(tc);

        for (InitialPosition pos : tc.initialPieces) {
            board.set(pos.position, pos.piece);
        }

        for (String s : tc.moves) {
            if (makeMove(s)) {
                System.out.println("illegal move");
                gameRunning = false;
                return;
            }
        }

        System.out.println(Utils.stringifyBoard(board.getBoardData()));
    }

    public boolean isRunning() {
        return gameRunning;
    }

    public boolean makeMove(String move) {
        System.out.println("move: " + move);
        return false;
    }

    private class Board {
        private String[][] board;

        public Board() {
            board = new String[5][5];
            for (char x = 'a'; x <= 'e'; x++) {
                for (char y = '1'; y <= '5'; y++) {
                    set(Character.toString(x) + y, " ");
                }
            }
        }

        private int convert(char c) {
            if ('a' <= c && c <= 'e') {
                return c - 'a';
            } else if ('1' <= c && c <= '5'){
                return c - '1';
            } else {
                throw new IllegalArgumentException("invalid position");
            }
        }

        public void set(String pos, String v) {
            int x = convert(pos.charAt(1));
            int y = convert(pos.charAt(0));
            board[y][x] = v;
        }

        public String get(String pos) {
            int x = convert(pos.charAt(1));
            int y = convert(pos.charAt(0));
            return board[y][x];
        }

        public String[][] getBoardData() {
            return board;
        }
    }
}
