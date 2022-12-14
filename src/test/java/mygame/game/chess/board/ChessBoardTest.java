package mygame.game.chess.board;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ChessBoardTest {

    ChessBoard chessBoard = new ChessBoard();

    @Test
    void init() {
        chessBoard.init();
        System.out.println(Arrays.deepToString(chessBoard.getBoard()));
    }
}