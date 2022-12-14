package mygame.game.chess.piece;

import mygame.point.Point;

public abstract class Piece {

    Point point;
    final String teamName;

    public Piece(Point point, String teamName) {
        this.point = point;
        this.teamName = teamName;
    }
}
