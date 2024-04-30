package chocola.chess;

interface Notation {

    String notate();

    static Notation getResultNotation(Result result) {
        return switch (result) {
            case WHITE_WIN -> WHITE_WIN;
            case BLACK_WIN -> BLACK_WIN;
            case DRAW -> DRAW;
            case NONE -> EMPTY;
        };
    }

    Notation KING_SIDE_CASTLING = () -> "O-O";
    Notation QUEEN_SIDE_CASTLING = () -> "O-O-O";
    Notation WHITE_WIN = () -> "1-0";
    Notation BLACK_WIN = () -> "0-1";
    Notation DRAW = () -> "1/2-1/2";
    Notation EMPTY = () -> "";
}
