package mygame.game.chess.turn;

import lombok.Data;
import mygame.piece.Piece;
import mygame.point.Point;

@Data
public class ChessNotation {

    Piece piece;
    Point startPoint;
    Point endPoint;
}
