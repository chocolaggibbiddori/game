package mygame.game.chess.piece;

import mygame.piece.Piece;
import mygame.point.Point;

public abstract class ChessPiece extends Piece {

    private int moveCount = 0;

    public ChessPiece(Point point, String teamName) {
        super(point, teamName);
    }

    public void addMoveCount() {
        moveCount++;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
