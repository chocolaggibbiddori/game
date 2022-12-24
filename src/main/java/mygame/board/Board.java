package mygame.board;

import mygame.piece.Piece;
import mygame.point.Point;

public interface Board {

    void init();

    Piece findByPoint(Point point);

    Piece findByPoint(int x, int y);

    Piece findByPoint(String strPoint);

    Piece move(Piece piece, Point endPoint);

    void clear();
}
