package mygame.game.chess.validation;

import mygame.game.chess.board.ChessBoard;
import mygame.game.chess.name.Name;
import mygame.game.chess.piece.*;
import mygame.game.chess.point.ChessPoint;
import mygame.game.chess.repository.ChessNotationRepository;
import mygame.game.chess.turn.ChessTurn;
import mygame.piece.Piece;
import mygame.point.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChessValidationTest {

    ChessBoard board = new ChessBoard();
    ChessNotationRepository repository = new ChessNotationRepository();
    ChessTurn turn = new ChessTurn(repository);
    ChessValidation validation = new ChessValidation(board, turn, repository);

    @AfterEach
    void afterEach() {
        board.clear();
    }

    @Test
    void pawnMoveList() {
        ChessPoint point = new ChessPoint("d2");
        Pawn pawn = new Pawn(point, Name.TEAM_WHITE);
        board.setInBoard(pawn, point);

        pawn.setFirstMoveToFalse();

        ChessPoint point2 = new ChessPoint("e3");
        Rook rook = new Rook(point2, Name.TEAM_BLACK);
        board.setInBoard(rook, point2);

        List<Point> moveList = validation.moveList(pawn);
        System.out.println("pawnMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("d3"),
                new ChessPoint("e3")
        );
    }

    @Test
    void rookMoveList() {
        ChessPoint point = new ChessPoint("a4");
        Rook rook = new Rook(point, Name.TEAM_WHITE);

        board.setInBoard(rook, point);
        Piece piece = board.findByPoint(point);
        assertThat(piece).isEqualTo(rook);

        ChessPoint point2 = new ChessPoint("d4");
        Pawn pawn = new Pawn(point2, Name.TEAM_BLACK);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(rook);
        System.out.println("rookMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("a1"),
                new ChessPoint("a2"),
                new ChessPoint("a3"),
                new ChessPoint("a5"),
                new ChessPoint("a6"),
                new ChessPoint("a7"),
                new ChessPoint("a8"),
                new ChessPoint("b4"),
                new ChessPoint("c4"),
                new ChessPoint("d4")
        );
    }

    @Test
    void knightMoveList() {
        ChessPoint point = new ChessPoint("e1");
        Knight knight = new Knight(point, Name.TEAM_WHITE);
        board.setInBoard(knight, point);

        ChessPoint point2 = new ChessPoint("d3");
        Pawn pawn = new Pawn(point2, Name.TEAM_BLACK);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(knight);
        System.out.println("knightMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("d3"),
                new ChessPoint("f3"),
                new ChessPoint("c2"),
                new ChessPoint("g2")
        );
    }

    @Test
    void bishopMoveList() {
        ChessPoint point = new ChessPoint("d4");
        Bishop bishop = new Bishop(point, Name.TEAM_WHITE);
        board.setInBoard(bishop, point);

        ChessPoint point2 = new ChessPoint("e5");
        Pawn pawn = new Pawn(point2, Name.TEAM_BLACK);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(bishop);
        System.out.println("bishopMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("c5"),
                new ChessPoint("b6"),
                new ChessPoint("a7"),
                new ChessPoint("e5"),
                new ChessPoint("c3"),
                new ChessPoint("b2"),
                new ChessPoint("a1"),
                new ChessPoint("e3"),
                new ChessPoint("f2"),
                new ChessPoint("g1")
        );
    }

    @Test
    void queenMoveList() {
        ChessPoint point = new ChessPoint("d4");
        Queen queen = new Queen(point, Name.TEAM_WHITE);
        board.setInBoard(queen, point);

        ChessPoint point2 = new ChessPoint("e5");
        Pawn pawn = new Pawn(point2, Name.TEAM_WHITE);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(queen);
        moveList.sort((c1, c2) -> {
            if (c1.getX() != c2.getX()) {
                return c1.getX() - c2.getX();
            }
            return c1.getY() - c2.getY();
        });
        System.out.println("queenMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("d5"),
                new ChessPoint("d6"),
                new ChessPoint("d7"),
                new ChessPoint("d8"),
                new ChessPoint("d3"),
                new ChessPoint("d2"),
                new ChessPoint("d1"),
                new ChessPoint("a4"),
                new ChessPoint("b4"),
                new ChessPoint("c4"),
                new ChessPoint("e4"),
                new ChessPoint("f4"),
                new ChessPoint("g4"),
                new ChessPoint("h4"),
                new ChessPoint("c5"),
                new ChessPoint("b6"),
                new ChessPoint("a7"),
                new ChessPoint("c3"),
                new ChessPoint("b2"),
                new ChessPoint("a1"),
                new ChessPoint("e3"),
                new ChessPoint("f2"),
                new ChessPoint("g1")
        );
    }

    @Test
    void kingMoveList() {
        ChessPoint point = new ChessPoint("d4");
        King king = new King(point, Name.TEAM_WHITE);
        board.setInBoard(king, point);

        ChessPoint point2 = new ChessPoint("e5");
        Pawn pawn = new Pawn(point2, Name.TEAM_BLACK);
        board.setInBoard(pawn, point2);

        List<Point> moveList = validation.moveList(king);
        System.out.println("kingMoveList = " + moveList);
        assertThat(moveList).contains(
                new ChessPoint("c5"),
                new ChessPoint("d5"),
                new ChessPoint("e5"),
                new ChessPoint("c4"),
                new ChessPoint("e4"),
                new ChessPoint("c3"),
                new ChessPoint("d3"),
                new ChessPoint("e3")
        );
    }
}