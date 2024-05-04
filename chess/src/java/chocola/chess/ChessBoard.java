package chocola.chess;

import chocola.chess.piece.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class ChessBoard implements Copyable<ChessBoard> {

    static final int FILE_NUM = 8;
    static final int RANK_NUM = 8;

    private final Piece[][] board;
    private final List<Notation> notationList;

    ChessBoard() {
        board = boardInit();
        notationList = new ArrayList<>();
    }

    private Piece[][] boardInit() {
        final Piece[][] board = new Piece[FILE_NUM][RANK_NUM];

        kingInit(board);
        queenInit(board);
        rookInit(board);
        knightInit(board);
        bishopInit(board);
        pawnInit(board);

        return board;
    }

    private void kingInit(Piece[][] board) {
        King whiteKing = new King(Team.WHITE);
        King blackKing = new King(Team.BLACK);

        setPiece(board, whiteKing, Tile.E1);
        setPiece(board, blackKing, Tile.E8);
    }

    private void queenInit(Piece[][] board) {
        Queen whiteQueen = new Queen(Team.WHITE);
        Queen blackQueen = new Queen(Team.BLACK);

        setPiece(board, whiteQueen, Tile.D1);
        setPiece(board, blackQueen, Tile.D8);
    }

    private void rookInit(Piece[][] board) {
        Rook whiteLeftRook = new Rook(Team.WHITE);
        Rook whiteRightRook = new Rook(Team.WHITE);
        Rook blackLeftRook = new Rook(Team.BLACK);
        Rook blackRightRook = new Rook(Team.BLACK);

        setPiece(board, whiteLeftRook, Tile.A1);
        setPiece(board, whiteRightRook, Tile.H1);
        setPiece(board, blackLeftRook, Tile.A8);
        setPiece(board, blackRightRook, Tile.H8);
    }

    private void knightInit(Piece[][] board) {
        Knight whiteLeftKnight = new Knight(Team.WHITE);
        Knight whiteRightKnight = new Knight(Team.WHITE);
        Knight blackLeftKnight = new Knight(Team.BLACK);
        Knight blackRightKnight = new Knight(Team.BLACK);

        setPiece(board, whiteLeftKnight, Tile.B1);
        setPiece(board, whiteRightKnight, Tile.G1);
        setPiece(board, blackLeftKnight, Tile.B8);
        setPiece(board, blackRightKnight, Tile.G8);
    }

    private void bishopInit(Piece[][] board) {
        Bishop whiteLeftBishop = new Bishop(Team.WHITE);
        Bishop whiteRightBishop = new Bishop(Team.WHITE);
        Bishop blackLeftBishop = new Bishop(Team.BLACK);
        Bishop blackRightBishop = new Bishop(Team.BLACK);

        setPiece(board, whiteLeftBishop, Tile.C1);
        setPiece(board, whiteRightBishop, Tile.F1);
        setPiece(board, blackLeftBishop, Tile.C8);
        setPiece(board, blackRightBishop, Tile.F8);
    }

    private void pawnInit(Piece[][] board) {
        for (int i = 0; i < FILE_NUM; i++) {
            Tile whitePawnTile = TileConverter.convertToTile(i, 1);
            Tile blackPawnTile = TileConverter.convertToTile(i, 6);

            setPiece(board, new Pawn(Team.WHITE), whitePawnTile);
            setPiece(board, new Pawn(Team.BLACK), blackPawnTile);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int rank = RANK_NUM; rank >= 0; rank--) {
            for (int file = 0; file < FILE_NUM; file++) {
                Tile tile = TileConverter.convertToTile(file, rank);
                Optional<Piece> pieceOpt = getPiece(tile);

                if (pieceOpt.isPresent()) {
                    Piece piece = pieceOpt.get();

                    if (piece instanceof Pawn) sb.append(piece.team == Team.WHITE ? "P" : "p");
                    else sb.append(piece.team == Team.WHITE ? piece.getInitial() : piece.getInitial().toLowerCase());
                } else {
                    sb.append("1");
                }
            }

            sb.append("\n");
        }

        return toString(sb);
    }

    private String toString(StringBuilder sb) {
        return sb.toString()
                .replaceAll("11111111", "8")
                .replaceAll("1111111", "7")
                .replaceAll("111111", "6")
                .replaceAll("11111", "5")
                .replaceAll("1111", "4")
                .replaceAll("111", "3")
                .replaceAll("11", "2");
    }

    void move(Tile target, Tile to) {
        Optional<Piece> pieceOpt = getPiece(target);
        if (pieceOpt.isEmpty()) throw new IllegalArgumentException();

        Piece piece = pieceOpt.get();
        Optional<Piece> enemyPieceOpt = move(piece, to);
        Piece enemyPiece = enemyPieceOpt.orElse(null);
        addNotation(piece, enemyPiece, target, to);
    }

    private Optional<Piece> move(Piece piece, Tile to) {
        Optional<Piece> enemyPieceOpt = getPiece(to);
        setPiece(piece, to);
        return enemyPieceOpt;
    }

    private void addNotation(Piece piece, Piece enemyPiece, Tile target, Tile to) {
        Notation notation = createNotation(piece, enemyPiece, target, to);
        notationList.add(notation);
    }

    private Notation createNotation(Piece piece, Piece enemyPiece, Tile target, Tile to) {
        if (isCastling(piece, target, to)) {
            if (to.getFile() == 'g') return Notation.KING_SIDE_CASTLING;
            else return Notation.QUEEN_SIDE_CASTLING;
        }

        String unitInitial = piece.getInitial();
        char assist = getAssist(piece, target, to);
        boolean capture = enemyPiece != null;
        String promotion = getPromotion(piece, to);
        AttackType attackType = getAttackType(piece);

        return new NormalNotation(unitInitial, assist, capture, to, promotion, attackType);
    }

    private boolean isCastling(Piece piece, Tile target, Tile to) {
        if (!(piece instanceof King)) return false;
        return Math.abs(target.getFile() - to.getFile()) == 2;
    }

    private char getAssist(Piece piece, Tile target, Tile to) {
        final ChessValidator validator = new ChessValidator(this);

        Optional<Piece> anotherOpt = getBoardStream()
                .filter(p -> p.getClass().equals(piece.getClass()))
                .filter(p -> p.team == piece.team)
                .filter(p -> p != piece)
                .filter(p -> validator.isValid(p.getPosition(), to))
                .findAny();

        if (anotherOpt.isEmpty()) return ' ';

        Piece another = anotherOpt.get();
        if (another.getPosition().getFileIdx() == target.getFileIdx()) return target.getRank();
        else return target.getFile();
    }

    private String getPromotion(Piece piece, Tile to) {
        Optional<Piece> pieceOpt = getPiece(to);
        if (pieceOpt.isPresent()) {
            Piece p = pieceOpt.get();
            if (piece != p) return p.getInitial();
        }

        return "";
    }

    private AttackType getAttackType(Piece piece) {
        final ChessValidator validator = new ChessValidator(this);

        if (validator.isCheckmate(piece.team)) return AttackType.CHECKMATE;
        if (validator.isCheck(piece.team)) return AttackType.CHECK;
        return AttackType.NONE;
    }

    Stream<Piece> getBoardStream() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream);
    }

    private void setPiece(Piece piece, Tile tile) {
        setPiece(board, piece, tile);
    }

    private void setPiece(Piece[][] board, Piece piece, Tile tile) {
        int file = tile.getFileIdx();
        int rank = tile.getRankIdx();

        piece.moveTo(tile);
        board[file][rank] = piece;
    }

    Optional<Piece> getPiece(Tile tile) {
        int file = tile.getFileIdx();
        int rank = tile.getRankIdx();
        Piece piece = board[file][rank];

        return Optional.ofNullable(piece);
    }

    List<Notation> getNotationList() {
        return List.copyOf(notationList);
    }

    @Override
    public ChessBoard copy() {
        ChessBoard clone = new ChessBoard();

        for (int file = 0; file < clone.board.length; file++) {
            for (int rank = 0; rank < clone.board[file].length; rank++) {
                Piece piece = this.board[file][rank];

                if (piece == null) clone.board[file][rank] = null;
                else clone.board[file][rank] = piece.copy();
            }
        }

        clone.notationList.addAll(this.notationList);

        return clone;
    }
}
