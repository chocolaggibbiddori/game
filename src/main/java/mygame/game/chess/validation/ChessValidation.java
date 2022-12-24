package mygame.game.chess.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.piece.*;
import mygame.game.chess.point.ChessPoint;
import mygame.game.chess.repository.ChessNotationRepository;
import mygame.game.chess.turn.ChessNotation;
import mygame.piece.Piece;
import mygame.point.Point;
import mygame.turn.Turn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChessValidation {

    private final Board chessBoard;
    private final Turn chessTurn;
    private final ChessNotationRepository notationRepository;

    public boolean checkStringPoint(String strPoint) {
        if (strPoint.length() != 2) {
            log.error("length={}", strPoint.length());
            return false;
        }

        char col = strPoint.charAt(0);
        if (col < 'a' || col > 'h') {
            log.error("column is out of board");
            return false;
        }

        char row = strPoint.charAt(1);
        if (row < '1' || row > '8') {
            log.error("row is out of board");
            return false;
        }

        return true;
    }

    public boolean isNull(ChessPoint point) {
        Piece piece = chessBoard.findByPoint(point);
        if (piece == null) {
            log.error("piece is null");
            return true;
        }
        return false;
    }

    public boolean isOurTeam(Piece piece) {
        if (!piece.getTeamName().equals(chessTurn.getCurrentTeam())) {
            log.error("not your turn");
            return false;
        }
        return true;
    }

    private boolean isOutOfBoard(Point point) {
        if (point.getX() < 0 || point.getX() > 7) {
            return true;
        }
        if (point.getY() < 0 || point.getY() > 7) {
            return true;
        }
        return false;
    }

    private boolean isOutOfBoard(int x, int y) {
        if (x < 0 || x > 7) {
            return true;
        }
        if (y < 0 || y > 7) {
            return true;
        }
        return false;
    }

    public List<Point> moveList(Piece piece) {
        if (piece == null) return null;
        if (piece instanceof Pawn) {
            return moveList((Pawn) piece);
        } else if (piece instanceof Rook) {
            return moveList((Rook) piece);
        } else if (piece instanceof Knight) {
            return moveList((Knight) piece);
        } else if (piece instanceof Bishop) {
            return moveList((Bishop) piece);
        } else if (piece instanceof Queen) {
            return moveList((Queen) piece);
        } else if (piece instanceof King) {
            return moveList((King) piece);
        }
        return null;
    }

    private List<Point> moveList(Pawn pawn) {
        List<Point> moveList = new ArrayList<>();
        int x = pawn.getPoint().getX();
        int y = pawn.getPoint().getY();

        moveForwardAtPawn(pawn, moveList, x, y);
        attackDiagonally(pawn, moveList, x, y);
        enpassant(pawn, moveList, x, y);
        return moveList;
    }

    private void moveForwardAtPawn(Pawn pawn, List<Point> moveList, int x, int y) {
        int front = x + pawn.getMoveDirect();
        Piece blockedPiece = chessBoard.findByPoint(front, y);

        if (blockedPiece != null) {
            return;
        }

        ChessPoint firstPoint = new ChessPoint(front, y);
        if (isOutOfBoard(firstPoint)) {
            return;
        }

        moveList.add(firstPoint);

        if (!pawn.isFirstMove()) {
            return;
        }

        int twoFront = front + pawn.getMoveDirect();
        ChessPoint secondPoint = new ChessPoint(twoFront, y);
        if (isOutOfBoard(secondPoint)) {
            return;
        }

        blockedPiece = chessBoard.findByPoint(secondPoint);
        if (blockedPiece == null) {
            moveList.add(secondPoint);
        }
    }

    private void attackDiagonally(Pawn pawn, List<Point> moveList, int x, int y) {
        int front = x + pawn.getMoveDirect();
        attackDiagonally(moveList, front, y - 1, pawn.getTeamName());
        attackDiagonally(moveList, front, y + 1, pawn.getTeamName());
    }

    private void attackDiagonally(List<Point> moveList, int x, int y, String teamName) {
        ChessPoint point = new ChessPoint(x, y);
        if (isOutOfBoard(point)) {
            return;
        }

        Piece piece = chessBoard.findByPoint(point);
        if (piece == null) {
            return;
        }

        if (piece.getTeamName().equals(teamName)) {
            return;
        }

        moveList.add(point);
    }

    private void enpassant(Pawn pawn, List<Point> moveList, int x, int y) {
        if (chessTurn.getCount() == 1) {
            return;
        }
        enpassant(pawn, moveList, x, y - 1, pawn.getTeamName());
        enpassant(pawn, moveList, x, y + 1, pawn.getTeamName());
    }

    private void enpassant(Pawn pawn, List<Point> moveList, int x, int y, String teamName) {
        ChessPoint point = new ChessPoint(x, y);
        if (isOutOfBoard(point)) {
            return;
        }

        Piece piece = chessBoard.findByPoint(point);
        if (piece == null) {
            return;
        }
        if (!(piece instanceof Pawn)) {
            return;
        }
        if (piece.getTeamName().equals(teamName)) {
            return;
        }

        ChessNotation preNotation = notationRepository.getNotation(chessTurn.getCount() - 1);
        if (preNotation.getPiece() != piece) {
            return;
        }

        if (((Pawn) piece).getMoveCount() == 1) {
            ChessPoint enpassantPoint = new ChessPoint(x + pawn.getMoveDirect(), y);
            moveList.add(enpassantPoint);
        }
    }

    // TODO: 2022/12/23 테스트
    private List<Point> moveList(Rook rook) {
        List<Point> moveList = new ArrayList<>();
        int x = rook.getPoint().getX();
        int y = rook.getPoint().getY();

        boolean moveMinusX = true;
        boolean movePlusX = true;
        boolean moveMinusY = true;
        boolean movePlusY = true;
        boolean isContinue = true;
        int i = 1;
        while (isContinue) {
            if (moveMinusX) {
                moveMinusX = moveAtRook(rook, moveList, x - i, y);
            }
            if (movePlusX) {
                movePlusX = moveAtRook(rook, moveList, x + i, y);
            }
            if (moveMinusY) {
                moveMinusY = moveAtRook(rook, moveList, x, y - i);
            }
            if (movePlusY) {
                movePlusY = moveAtRook(rook, moveList, x, y + i);
            }

            isContinue = moveMinusX || movePlusX || moveMinusY || movePlusY;
            i++;
        }

        return moveList;
    }

    private boolean moveAtRook(Rook rook, List<Point> moveList, int x, int y) {
        ChessPoint point = new ChessPoint(x, y);
        if (isOutOfBoard(point)) {
            return false;
        }

        Piece piece = chessBoard.findByPoint(point);
        if (piece == null) {
            moveList.add(point);
            return true;
        }
        if (!piece.getTeamName().equals(rook.getTeamName())) {
            moveList.add(point);
        }
        return false;
    }

    private List<Point> moveList(Knight knight) {
        List<Point> moveList = new ArrayList<>();
        return moveList;
    }

    private List<Point> moveList(Bishop bishop) {
        List<Point> moveList = new ArrayList<>();
        return moveList;
    }

    private List<Point> moveList(Queen queen) {
        List<Point> moveList = new ArrayList<>();
        return moveList;
    }

    private List<Point> moveList(King king) {
        List<Point> moveList = new ArrayList<>();
        return moveList;
    }
}
