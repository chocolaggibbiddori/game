package mygame.turn;

import mygame.piece.Piece;
import mygame.point.Point;

public class Notation {

    Piece piece;
    Point startPoint;
    Point endPoint;

    public Notation(Piece piece, Point startPoint, Point endPoint) {
        this.piece = piece;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
