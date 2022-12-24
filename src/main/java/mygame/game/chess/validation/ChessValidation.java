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

    public boolean isRightStringPoint(String strPoint) {
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
        ChessPiece piece = (ChessPiece) chessBoard.findByPoint(point);
        if (piece == null) {
            log.error("piece is null");
            return true;
        }
        return false;
    }

    public boolean isOurTeam(ChessPiece piece) {
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

    public List<ChessPoint> moveList(ChessPiece piece) {
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

    private List<ChessPoint> moveList(Pawn pawn) {
        List<ChessPoint> moveList = new ArrayList<>();
        int x = pawn.getPoint().getX();
        int y = pawn.getPoint().getY();

        moveForwardAtPawn(pawn, moveList, x, y);
        attackDiagonally(pawn, moveList, x, y);
        enpassant(pawn, moveList, x, y);
        return moveList;
    }

    private void moveForwardAtPawn(Pawn pawn, List<ChessPoint> moveList, int x, int y) {
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

    private void attackDiagonally(Pawn pawn, List<ChessPoint> moveList, int x, int y) {
        int front = x + pawn.getMoveDirect();
        attackDiagonally(moveList, front, y - 1, pawn.getTeamName());
        attackDiagonally(moveList, front, y + 1, pawn.getTeamName());
    }

    private void attackDiagonally(List<ChessPoint> moveList, int x, int y, String teamName) {
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

    private void enpassant(Pawn pawn, List<ChessPoint> moveList, int x, int y) {
        if (chessTurn.getCount() == 1) {
            return;
        }
        enpassant(pawn, moveList, x, y - 1, pawn.getTeamName());
        enpassant(pawn, moveList, x, y + 1, pawn.getTeamName());
    }

    private void enpassant(Pawn pawn, List<ChessPoint> moveList, int x, int y, String teamName) {
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
        if (preNotation.getChessPiece() != piece) {
            return;
        }

        if (((Pawn) piece).getMoveCount() == 1) {
            ChessPoint enpassantPoint = new ChessPoint(x + pawn.getMoveDirect(), y);
            moveList.add(enpassantPoint);
        }
    }

    private List<ChessPoint> moveList(Rook rook) {
        List<ChessPoint> moveList = new ArrayList<>();
        int x = rook.getPoint().getX();
        int y = rook.getPoint().getY();

        boolean direct1 = true;
        boolean direct2 = true;
        boolean direct3 = true;
        boolean direct4 = true;
        boolean isContinue = true;
        int i = 1;
        while (isContinue) {
            if (direct1) {
                direct1 = checkPointOnMoveInLine(rook.getTeamName(), moveList, x - i, y);
            }
            if (direct2) {
                direct2 = checkPointOnMoveInLine(rook.getTeamName(), moveList, x + i, y);
            }
            if (direct3) {
                direct3 = checkPointOnMoveInLine(rook.getTeamName(), moveList, x, y - i);
            }
            if (direct4) {
                direct4 = checkPointOnMoveInLine(rook.getTeamName(), moveList, x, y + i);
            }

            isContinue = direct1 || direct2 || direct3 || direct4;
            i++;
        }

        return moveList;
    }

    private boolean checkPointOnMoveInLine(String teamName, List<ChessPoint> moveList, int x, int y) {
        ChessPoint point = new ChessPoint(x, y);
        if (isOutOfBoard(point)) {
            return false;
        }

        Piece piece = chessBoard.findByPoint(point);
        if (piece == null) {
            moveList.add(point);
            return true;
        }
        if (!piece.getTeamName().equals(teamName)) {
            moveList.add(point);
        }
        return false;
    }

    private List<ChessPoint> moveList(Knight knight) {
        List<ChessPoint> moveList = new ArrayList<>();
        int[][] checkList = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {1, -2}, {-1, 2}, {1, 2}};
        int x = knight.getPoint().getX();
        int y = knight.getPoint().getY();

        for (int[] check : checkList) {
            int checkX = x + check[0];
            int checkY = y + check[1];

            ChessPoint point = new ChessPoint(checkX, checkY);
            if (isOutOfBoard(point)) {
                continue;
            }

            Piece piece = chessBoard.findByPoint(point);
            if (piece == null) {
                moveList.add(point);
                continue;
            }
            if (!piece.getTeamName().equals(knight.getTeamName())) {
                moveList.add(point);
            }
        }

        return moveList;
    }

    private List<ChessPoint> moveList(Bishop bishop) {
        List<ChessPoint> moveList = new ArrayList<>();
        int x = bishop.getPoint().getX();
        int y = bishop.getPoint().getY();

        boolean direct1 = true;
        boolean direct2 = true;
        boolean direct3 = true;
        boolean direct4 = true;
        boolean isContinue = true;
        int i = 1;
        while (isContinue) {
            if (direct1) {
                direct1 = checkPointOnMoveInLine(bishop.getTeamName(), moveList, x + i, y + i);
            }
            if (direct2) {
                direct2 = checkPointOnMoveInLine(bishop.getTeamName(), moveList, x + i, y - i);
            }
            if (direct3) {
                direct3 = checkPointOnMoveInLine(bishop.getTeamName(), moveList, x - i, y + i);
            }
            if (direct4) {
                direct4 = checkPointOnMoveInLine(bishop.getTeamName(), moveList, x - i, y - i);
            }

            isContinue = direct1 || direct2 || direct3 || direct4;
            i++;
        }

        return moveList;
    }

    private List<ChessPoint> moveList(Queen queen) {
        List<ChessPoint> moveList = new ArrayList<>();
        int x = queen.getPoint().getX();
        int y = queen.getPoint().getY();

        boolean direct1 = true;
        boolean direct2 = true;
        boolean direct3 = true;
        boolean direct4 = true;
        boolean direct5 = true;
        boolean direct6 = true;
        boolean direct7 = true;
        boolean direct8 = true;
        boolean isContinue = true;
        int i = 1;
        while (isContinue) {
            if (direct1) {
                direct1 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x + i, y);
            }
            if (direct2) {
                direct2 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x - i, y);
            }
            if (direct3) {
                direct3 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x, y + i);
            }
            if (direct4) {
                direct4 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x, y - i);
            }
            if (direct5) {
                direct5 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x + i, y + i);
            }
            if (direct6) {
                direct6 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x + i, y - i);
            }
            if (direct7) {
                direct7 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x - i, y + i);
            }
            if (direct8) {
                direct8 = checkPointOnMoveInLine(queen.getTeamName(), moveList, x - i, y - i);
            }

            isContinue = direct1 || direct2 || direct3 || direct4 || direct5 || direct6 || direct7 || direct8;
            i++;
        }

        return moveList;
    }

    private List<ChessPoint> moveList(King king) {
        List<ChessPoint> moveList = new ArrayList<>();
        int[][] checkList = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int x = king.getPoint().getX();
        int y = king.getPoint().getY();

        for (int[] check : checkList) {
            int checkX = x + check[0];
            int checkY = y + check[1];

            ChessPoint point = new ChessPoint(checkX, checkY);
            if (isOutOfBoard(point)) {
                continue;
            }

            Piece piece = chessBoard.findByPoint(point);
            if (piece == null) {
                moveList.add(point);
                continue;
            }
            if (!piece.getTeamName().equals(king.getTeamName())) {
                moveList.add(point);
            }
        }

        return moveList;
    }
}
