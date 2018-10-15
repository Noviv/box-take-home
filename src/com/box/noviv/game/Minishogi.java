package com.box.noviv.game;

import com.box.noviv.pieces.*;
import com.box.noviv.utils.Coordinate;
import com.box.noviv.utils.Utils;
import com.box.noviv.utils.Utils.InitialPosition;
import com.box.noviv.utils.Utils.TestCase;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public boolean isRunning() {
        return gameRunning;
    }

    public String getErrno() {
        return errno;
    }

    public void prompt() {
        System.out.println(Utils.stringifyBoard(board.getBoardData()));
        Collectors.joining(",");
        System.out.println("Captures UPPER: " + upperCaptures.stream().map(Object::toString).collect(Collectors.joining(" ")));
        System.out.println("Captures lower: " + lowerCaptures.stream().map(Object::toString).collect(Collectors.joining(" ")));
        System.out.println();

        board.printCheckStatus(upperTurn);

        if (gameRunning) {
            System.out.print((upperTurn ? "UPPER" : "lower") + ">");
        } else {
            if (uTurnCount >= 200 && lTurnCount >= 200) {
                System.out.println("Tie game.  " + getErrno());
            } else {
                System.out.println((!upperTurn ? "UPPER" : "lower") + " player wins.  " + getErrno());
            }
        }
    }

    public void makeMove(String... cmd) {
        assert cmd.length > 2 : "invalid number of commands";

        if (cmd[0].equals("move")) {
            GamePiece src = board.get(cmd[1]);
            GamePiece dst = board.get(cmd[2]);

            if (src == null) {
                errno = "no piece at location";
                gameRunning = false;
                return;
            }

            if (src.isUpperPiece() != upperTurn) {
                errno = "piece not owned";
                gameRunning = false;
                return;
            }

            if (dst != null && dst.isUpperPiece() == upperTurn) {
                errno = "taking piece that is owned";
                gameRunning = false;
                return;
            }

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
                if (c.y == 0 || c.y == 4) {
                    src.setPromoted(true);
                }
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
            boolean found = false;
            for (int i = 0; !found && i < (upperTurn ? upperCaptures : lowerCaptures).size(); i++) {
                if ((upperTurn ? upperCaptures : lowerCaptures).get(i).getRepr().toLowerCase().contains(cmd[1])) {
                    if (board.get(cmd[2]) != null) {
                        errno = "Illegal move.";
                        gameRunning = false;
                        return;
                    }

                    Coordinate place = board.convert(cmd[2]);
                    if ((upperTurn ? upperCaptures : lowerCaptures).get(i) instanceof Pawn) {
                        for (int r = 0; r < 5; r++) {
                            if (r != place.y) {
                                GamePiece gp = board.get(place.x, r);
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
                        if ((gp.isUpperPiece() && place.y == 0) ||
                                (!gp.isUpperPiece() && place.y == 4)) {
                            errno = "Illegal move.";
                            gameRunning = false;
                            return;
                        }
                    }
                    board.set(cmd[2], (upperTurn ? upperCaptures : lowerCaptures).remove(i));
                    found = true;
                }
            }
            if (!found) {
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

        if (uTurnCount >= 200 && lTurnCount >= 200) {
            gameRunning = false;
            errno = "Too many moves.";
            return;
        }

        upperTurn = !upperTurn;
    }


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