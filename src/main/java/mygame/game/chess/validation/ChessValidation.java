package mygame.game.chess.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.point.ChessPoint;
import mygame.piece.Piece;
import mygame.turn.Turn;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChessValidation {

    private final Board chessBoard;
    private final Turn chessTurn;

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
}
