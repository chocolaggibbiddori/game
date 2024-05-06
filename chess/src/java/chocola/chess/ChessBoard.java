package chocola.chess;

import chocola.chess.piece.*;

import java.util.*;
import java.util.stream.Stream;

public class ChessBoard implements Copyable<ChessBoard> {

    static final int FILE_NUM = 8;
    static final int RANK_NUM = 8;

    private final Piece[][] board;
    private final List<Notation> notationList;
    private final ChessValidator validator;

    ChessBoard() {
        board = boardInit();
        notationList = new ArrayList<>();
        validator = new ChessValidator();
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
        Tile whiteKingPosition = Tile.E1;
        Tile blackKingPosition = Tile.E8;
        King whiteKing = new King(Team.WHITE, whiteKingPosition);
        King blackKing = new King(Team.BLACK, blackKingPosition);

        setPiece(board, whiteKing, whiteKingPosition);
        setPiece(board, blackKing, blackKingPosition);
    }

    private void queenInit(Piece[][] board) {
        Tile whiteQueenPosition = Tile.D1;
        Tile blackQueenPosition = Tile.D8;
        Queen whiteQueen = new Queen(Team.WHITE, whiteQueenPosition);
        Queen blackQueen = new Queen(Team.BLACK, blackQueenPosition);

        setPiece(board, whiteQueen, whiteQueenPosition);
        setPiece(board, blackQueen, blackQueenPosition);
    }

    private void rookInit(Piece[][] board) {
        Team whiteTeam = Team.WHITE;
        Team blackTeam = Team.BLACK;
        Tile whiteLeftRookPosition = Tile.A1;
        Tile whiteRightRookPosition = Tile.H1;
        Tile blackLeftRookPosition = Tile.A8;
        Tile blackRightRookPosition = Tile.H8;
        Rook whiteLeftRook = new Rook(whiteTeam, whiteLeftRookPosition);
        Rook whiteRightRook = new Rook(whiteTeam, whiteRightRookPosition);
        Rook blackLeftRook = new Rook(blackTeam, blackLeftRookPosition);
        Rook blackRightRook = new Rook(blackTeam, blackRightRookPosition);

        setPiece(board, whiteLeftRook, whiteLeftRookPosition);
        setPiece(board, whiteRightRook, whiteRightRookPosition);
        setPiece(board, blackLeftRook, blackLeftRookPosition);
        setPiece(board, blackRightRook, blackRightRookPosition);
    }

    private void knightInit(Piece[][] board) {
        Team whiteTeam = Team.WHITE;
        Team blackTeam = Team.BLACK;
        Tile whiteLeftKnightPosition = Tile.B1;
        Tile whiteRightKnightPosition = Tile.G1;
        Tile blackLeftKnightPosition = Tile.B8;
        Tile blackRightKnightPosition = Tile.G8;
        Knight whiteLeftKnight = new Knight(whiteTeam, whiteLeftKnightPosition);
        Knight whiteRightKnight = new Knight(whiteTeam, whiteRightKnightPosition);
        Knight blackLeftKnight = new Knight(blackTeam, blackLeftKnightPosition);
        Knight blackRightKnight = new Knight(blackTeam, blackRightKnightPosition);

        setPiece(board, whiteLeftKnight, whiteLeftKnightPosition);
        setPiece(board, whiteRightKnight, whiteRightKnightPosition);
        setPiece(board, blackLeftKnight, blackLeftKnightPosition);
        setPiece(board, blackRightKnight, blackRightKnightPosition);
    }

    private void bishopInit(Piece[][] board) {
        Team whiteTeam = Team.WHITE;
        Team blackTeam = Team.BLACK;
        Tile whiteLeftBishopPosition = Tile.C1;
        Tile whiteRightBishopPosition = Tile.F1;
        Tile blackLeftBishopPosition = Tile.C8;
        Tile blackRightBishopPosition = Tile.F8;
        Bishop whiteLeftBishop = new Bishop(whiteTeam, whiteLeftBishopPosition);
        Bishop whiteRightBishop = new Bishop(whiteTeam, whiteRightBishopPosition);
        Bishop blackLeftBishop = new Bishop(blackTeam, blackLeftBishopPosition);
        Bishop blackRightBishop = new Bishop(blackTeam, blackRightBishopPosition);

        setPiece(board, whiteLeftBishop, whiteLeftBishopPosition);
        setPiece(board, whiteRightBishop, whiteRightBishopPosition);
        setPiece(board, blackLeftBishop, blackLeftBishopPosition);
        setPiece(board, blackRightBishop, blackRightBishopPosition);
    }

    private void pawnInit(Piece[][] board) {
        for (int file = 0; file < FILE_NUM; file++) {
            Tile whitePawnTile = TileConverter.convertToTile(file, 1);
            Tile blackPawnTile = TileConverter.convertToTile(file, 6);
            Pawn whitePawn = new Pawn(Team.WHITE, whitePawnTile);
            Pawn blackPawn = new Pawn(Team.BLACK, blackPawnTile);

            setPiece(board, whitePawn, whitePawnTile);
            setPiece(board, blackPawn, blackPawnTile);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int rank = RANK_NUM - 1; rank >= 0; rank--) {
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
        movePiece(piece, to);
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
        boolean capture = enemyPiece != null;
        char assist;
        if (piece instanceof Pawn && capture) assist = piece.getPosition().getFile();
        else assist = getAssist(piece, target, to);
        String promotion = getPromotion(piece, to);
        AttackType attackType = getAttackType(piece);

        return new NormalNotation(unitInitial, assist, capture, to, promotion, attackType);
    }

    private boolean isCastling(Piece piece, Tile target, Tile to) {
        if (!(piece instanceof King)) return false;
        return Math.abs(target.getFile() - to.getFile()) == 2;
    }

    private char getAssist(Piece piece, Tile target, Tile to) {
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
        if (validator.isCheckmate(piece.team)) return AttackType.CHECKMATE;
        if (validator.isCheck(piece.team)) return AttackType.CHECK;

        return AttackType.NONE;
    }

    Stream<Piece> getBoardStream() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull);
    }

    private void setPiece(Piece[][] board, Piece piece, Tile tile) {
        int file = tile.getFileIdx();
        int rank = tile.getRankIdx();

        board[file][rank] = piece;
    }

    private void movePiece(Piece piece, Tile tile) {
        movePiece(board, piece, tile);
    }

    private void movePiece(Piece[][] board, Piece piece, Tile tile) {
        Tile position = piece.getPosition();
        setTileEmpty(board, position);

        int file = tile.getFileIdx();
        int rank = tile.getRankIdx();

        piece.moveTo(tile);
        board[file][rank] = piece;
    }

    private void setTileEmpty(Piece[][] board, Tile tile) {
        int pTile = tile.getFileIdx();
        int pRank = tile.getRankIdx();

        board[pTile][pRank] = null;
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

        for (int file = 0; file < FILE_NUM; file++) {
            for (int rank = 0; rank < RANK_NUM; rank++) {
                Piece piece = this.board[file][rank];

                if (piece == null) clone.board[file][rank] = null;
                else clone.board[file][rank] = piece.copy();
            }
        }

        clone.notationList.addAll(this.notationList);

        return clone;
    }

    public ChessValidator getValidator() {
        return validator;
    }

    public class ChessValidator {

        private static final Map<Class<? extends Piece>, Validator> VALIDATOR_MAP = validatorMapInit();

        private ChessValidator() {
        }

        private static Map<Class<? extends Piece>, Validator> validatorMapInit() {
            Map<Class<? extends Piece>, Validator> map = new HashMap<>();

            map.put(King.class, new KingValidator());
            map.put(Queen.class, new QueenValidator());
            map.put(Rook.class, new RookValidator());
            map.put(Knight.class, new KnightValidator());
            map.put(Bishop.class, new BishopValidator());
            map.put(Pawn.class, new PawnValidator());

            return map;
        }

        public boolean isValid(Tile target, Tile to) {
            Optional<Piece> targetPieceOpt = getPiece(target);
            if (targetPieceOpt.isEmpty()) return false;

            Piece targetPiece = targetPieceOpt.get();
            if (!targetPiece.canMoveTo(to)) return false;

            Optional<Piece> toPieceOpt = getPiece(to);
            if (toPieceOpt.isPresent()) {
                Piece toPiece = toPieceOpt.get();

                if (targetPiece.team == toPiece.team) return false;
            }

            ChessBoard chessBoard = ChessBoard.this;
            Class<? extends Piece> pieceClass = targetPiece.getClass();
            Validator validator = VALIDATOR_MAP.get(pieceClass);
            if (!validator.isValid(chessBoard.copy(), targetPiece, to)) return false;

            return !isChecked(chessBoard, target, to);
        }

        Result getResult(Team attackTeam) {
            if (isCheckmate(Team.WHITE)) return Result.BLACK_WIN;
            if (isCheckmate(Team.BLACK)) return Result.WHITE_WIN;
            if (isStalemate(attackTeam)) return Result.DRAW;

            return Result.NONE;
        }

        boolean isCheck(Team attackTeam) {
            Tile enemyKingPosition = getEnemyKingPosition(attackTeam);

            return getBoardStream()
                    .filter(p -> p.team == attackTeam)
                    .anyMatch(p -> {
                        if (p instanceof Pawn pawn) return pawn.canAttackTo(enemyKingPosition);
                        return isValid(p.getPosition(), enemyKingPosition);
                    });
        }

        private Tile getEnemyKingPosition(Team attackTeam) {
            Optional<Piece> enemyKingOpt = getBoardStream()
                    .filter(p -> p.team != attackTeam)
                    .filter(p -> p instanceof King)
                    .findFirst();

            if (enemyKingOpt.isEmpty()) throw new IllegalStateException();
            Piece enemyKing = enemyKingOpt.get();

            return enemyKing.getPosition();
        }

        boolean isCheckmate(Team attackTeam) {
            return isCheck(attackTeam) &&
                   cannotMove(attackTeam);
        }

        boolean isStalemate(Team attackTeam) {
            return !isCheck(attackTeam) &&
                   cannotMove(attackTeam);
        }

        private boolean cannotMove(Team attackTeam) {
            return getBoardStream()
                    .filter(p -> p.team != attackTeam)
                    .noneMatch(p -> {
                        Set<Tile> moveableTileSet = p.getMoveableTileSet();

                        for (Tile tile : moveableTileSet)
                            if (isValid(p.getPosition(), tile))
                                return true;

                        return false;
                    });
        }

        static boolean isChecked(ChessBoard chessBoard, Tile target, Tile to) {
            chessBoard = chessBoard.copy();
            Piece piece = chessBoard.getPiece(target).orElseThrow();

            chessBoard.movePiece(piece, to);

            ChessValidator validator = chessBoard.getValidator();
            return validator.isCheck(Team.getEnemyTeam(piece.team));
        }
    }
}
