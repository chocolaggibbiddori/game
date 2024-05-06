package chocola.chess;

import chocola.chess.piece.Piece;

interface Validator {

    boolean isValid(ChessBoard chessBoard, Piece piece, Tile to);
}
