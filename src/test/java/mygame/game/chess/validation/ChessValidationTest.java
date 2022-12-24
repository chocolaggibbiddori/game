package mygame.game.chess.validation;

import mygame.game.chess.board.ChessBoard;
import mygame.game.chess.name.Name;
import mygame.game.chess.piece.Pawn;
import mygame.game.chess.piece.Rook;
import mygame.game.chess.point.ChessPoint;
import mygame.game.chess.repository.ChessNotationRepository;
import mygame.game.chess.turn.ChessTurn;
import mygame.piece.Piece;
import mygame.point.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChessValidationTest {

    ChessBoard board = new ChessBoard();
    ChessNotationRepository repository = new ChessNotationRepository();
    ChessTurn turn = new ChessTurn(repository);
    ChessValidation validation = new ChessValidation(board, turn, repository);

    @Test
    void pawnMoveList() {
        ChessPoint point = new ChessPoint("d2");
        Pawn pawn = new Pawn(point, Name.TEAM_WHITE);
        board.setInBoard(pawn, point);

        pawn.setFirstMoveToFalse();

        ChessPoint point2 = new ChessPoint("e3");
        Rook rook = new Rook(point2, Name.TEAM_WHITE);
        board.setInBoard(rook, point2);

        List<Point> moveList = validation.moveList(pawn);
        System.out.println("moveList = " + moveList);
    }

    @Test
    void rookMoveList() {
        ChessPoint point = new ChessPoint("a1");
        Rook rook = new Rook(point, Name.TEAM_WHITE);

        board.setInBoard(rook, point);
        Piece piece = board.findByPoint(point);
        assertThat(piece).isEqualTo(rook);

        ChessPoint point2 = new ChessPoint("d4");
        Pawn pawn = new Pawn(point2, Name.TEAM_BLACK);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(rook);
        System.out.println(moveList);
    }
}