package mygame.turn;

import lombok.Data;
import mygame.piece.Piece;
import mygame.point.Point;

@Data
public class Notation {

    Piece piece;
    Point startPoint;
    Point endPoint;
}
