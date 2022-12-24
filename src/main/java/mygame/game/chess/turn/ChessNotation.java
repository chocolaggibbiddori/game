package mygame.game.chess.turn;

import lombok.Data;
import mygame.game.chess.piece.ChessPiece;
import mygame.point.Point;

@Data
public class ChessNotation {

    ChessPiece chessPiece;
    Point startPoint;
    Point endPoint;
}
