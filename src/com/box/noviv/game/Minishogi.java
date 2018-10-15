package com.box.noviv.game;

import com.box.noviv.pieces.*;
import com.box.noviv.utils.Coordinate;
import com.box.noviv.utils.Utils;
import com.box.noviv.utils.Utils.InitialPosition;
import com.box.noviv.utils.Utils.TestCase;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * State machine for Minishogi.
 */
public class Minishogi {
    private TestCase tc;
    private Board board = new Board();
    private boolean gameRunning = true;
    private String errno = "";

    private boolean upperTurn = false;

    private ArrayList<GamePiece> upperCaptures = new ArrayList<>();
    private ArrayList<GamePiece> lowerCaptures = new ArrayList<>();

    private int uTurnCount = 0;
    private int lTurnCount = 0;

    /**
     * @param filePath Path of initial test case file.
     */
    public Minishogi(String filePath) {
        try {
            tc = Utils.parseTestCase(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            gameRunning = false;
        }

        for (InitialPosition ip : tc.initialPieces) {
            board.set(ip.position, getPiece(ip.piece));
        }

        for (String c : tc.upperCaptures) {
            if (!c.isEmpty()) {
                upperCaptures.add(getPiece(c));
            }
        }
        for (String c : tc.lowerCaptures) {
            if (!c.isEmpty()) {
                lowerCaptures.add(getPiece(c));
            }
        }

        for (String s : tc.moves) {
            makeMove(s.split(" "));
            if (!isRunning()) {
                if (!tc.moves.isEmpty()) {
                    System.out.println((upperTurn ? "UPPER" : "lower") + " player action: " + tc.moves.get(tc.moves.size() - 1));
                }
                return;
            }
        }
        if (!tc.moves.isEmpty()) {
            System.out.println((!upperTurn ? "UPPER" : "lower") + " player action: " + tc.moves.get(tc.moves.size() - 1));
        }
    }

    /**
     * @return True if the state machine is in a valid/runnable state.
     */
    public boolean isRunning() {
        return gameRunning;
    }

    /**
     * @return Get last error status.
     */
    public String getErrno() {
        return errno;
    }

    /**
     * Print user input/status update to stdout.
     */
    public void prompt() {
        System.out.println(Utils.stringifyBoard(board.getBoardData()));
        Collectors.joining(",");
        System.out.println("Captures UPPER: " + upperCaptures.stream().map(Object::toString).collect(Collectors.joining(" ")));
        System.out.println("Captures lower: " + lowerCaptures.stream().map(Object::toString).collect(Collectors.joining(" ")));
        System.out.println();

        if (!gameRunning) {
            System.out.println((!upperTurn ? "UPPER" : "lower") + " player wins.  " + getErrno());
            return;
        }

        ArrayList<String> uncheckMoves = board.getCheckStatus(upperTurn);
        if (uncheckMoves != null) {
            if (uncheckMoves.isEmpty()) {
                gameRunning = false;
                System.out.println((!upperTurn ? "UPPER" : "lower") + " player wins.  Checkmate.");
                return;
            }

            System.out.println((upperTurn ? "UPPER" : "lower") + " player is in check!");
            System.out.println("Available moves:");
            for (String move : uncheckMoves) {
                System.out.println(move);
            }
        }

        if (uTurnCount >= 200 && lTurnCount >= 200) {
            System.out.println("Tie game.  Too many moves.");
            gameRunning = false;
            return;
        }

        System.out.print((upperTurn ? "UPPER" : "lower") + ">");
    }

    /**
     * @param cmd Array containing command/arguments to run in state machine.
     */
    public void makeMove(String... cmd) {
        assert cmd.length > 2 : "invalid number of commands";

        if (uTurnCount >= 200 && lTurnCount >= 200) {
            gameRunning = false;
            errno = "Too many moves.";
            upperTurn = !upperTurn;
            return;
        }

        if (cmd[0].equals("move")) {
            GamePiece src = board.get(cmd[1]);
            GamePiece dst = board.get(cmd[2]);

            if (!src.validMove(cmd[1], cmd[2], board)) {
                errno = "Illegal move.";
                gameRunning = false;
                return;
            }

            if (cmd.length > 3 && cmd[3].equals("promote")) {
                if (src.isPromoted()) {
                    errno = "Illegal move.";
                    gameRunning = false;
                    return;
                }
                if (src instanceof King || src instanceof GoldGeneral) {
                    errno = "Illegal move.";
                    gameRunning = false;
                    return;
                }
                src.setPromoted(true);
            }

            if (src instanceof Pawn) {
                Coordinate c = board.convert(cmd[2]);
                if (c.vert == 0 || c.vert == 4) {
                    src.setPromoted(true);
                }
            }

            // ensure not moving into check
            board.set(cmd[2], src);
            board.set(cmd[1], null);

            if (board.getCheckStatus(upperTurn) != null) {
                board.set(cmd[2], dst);
                board.set(cmd[1], src);
                errno = "Illegal move.";
                gameRunning = false;
                return;
            }

            // modification
            if (dst != null) {
                dst.setUpperPiece(!dst.isUpperPiece());
                dst.setPromoted(false);
                if (upperTurn) {
                    upperCaptures.add(dst);
                } else {
                    lowerCaptures.add(dst);
                }
            }

            board.set(cmd[2], src);
            board.set(cmd[1], null);
        } else if (cmd[0].equals("drop")) {
            if (board.get(cmd[2]) != null) {
                errno = "Illegal move.";
                gameRunning = false;
                return;
            }

            boolean found = false;
            for (int i = 0; !found && i < (upperTurn ? upperCaptures : lowerCaptures).size(); i++) {
                if ((upperTurn ? upperCaptures : lowerCaptures).get(i).getRepr().toLowerCase().contains(cmd[1])) {
                    // can't have two same-side pawns in the same columns
                    Coordinate place = board.convert(cmd[2]);
                    if ((upperTurn ? upperCaptures : lowerCaptures).get(i) instanceof Pawn) {
                        for (int r = 0; r < 5; r++) {
                            if (r != place.vert) {
                                GamePiece gp = board.get(place.horiz, r);
                                if (gp != null && gp instanceof Pawn && gp.isUpperPiece() == (upperTurn ? upperCaptures : lowerCaptures).get(i).isUpperPiece()) {
                                    errno = "Illegal move.";
                                    gameRunning = false;
                                    return;
                                }
                            }
                        }
                    }

                    GamePiece gp = (upperTurn ? upperCaptures : lowerCaptures).get(i);
                    if (gp instanceof Pawn) {
                        // can't place pawn in promotion zone opposite side
                        if ((gp.isUpperPiece() && place.vert == 0) ||
                                (!gp.isUpperPiece() && place.vert == 4)) {
                            errno = "Illegal move.";
                            gameRunning = false;
                            return;
                        }

                        // check that pawn isn't checkmating
                        GamePiece tmp = board.get(cmd[2]);
                        board.set(cmd[2], gp);
                        ArrayList<String> uncheckMoves = board.getCheckStatus(!upperTurn);
                        if (uncheckMoves != null && uncheckMoves.isEmpty()) {
                            board.set(cmd[2], tmp);
                            errno = "Illegal move.";
                            gameRunning = false;
                            return;
                        }
                    }

                    board.set(cmd[2], gp);
                    (upperTurn ? upperCaptures : lowerCaptures).remove(i);
                    found = true;
                }
            }
            if (!found) { // could not find piece to drop
                errno = "Illegal move.";
                gameRunning = false;
                return;
            }
        }

        if (upperTurn) {
            uTurnCount++;
        } else {
            lTurnCount++;
        }

        upperTurn = !upperTurn;
    }


    /**
     * @param piece Parse piece from this string.
     * @return GamePiece representing parsed string.
     */
    private GamePiece getPiece(String piece) {
        boolean promote = piece.charAt(0) == '+';
        if (promote) {
            piece = piece.substring(1);
        }
        String pieceLower = piece.toLowerCase();

        GamePiece gp;

        switch (pieceLower.charAt(0)) {
            case 'k':
                gp = new King();
                break;
            case 'r':
                gp = new Rook();
                break;
            case 'b':
                gp = new Bishop();
                break;
            case 'g':
                gp = new GoldGeneral();
                break;
            case 's':
                gp = new SilverGeneral();
                break;
            case 'p':
                gp = new Pawn();
                break;
            default:
                throw new IllegalStateException("trying to initialize invalid piece: " + piece);
        }

        gp.setPromoted(promote);
        gp.setUpperPiece(!pieceLower.equals(piece));

        return gp;
    }
}