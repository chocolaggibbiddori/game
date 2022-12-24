package mygame.game.chess.board;

import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.name.Name;
import mygame.game.chess.piece.*;
import mygame.game.chess.point.ChessPoint;
import mygame.piece.Piece;
import mygame.point.Point;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class ChessBoard implements Board {

    private static final ChessPiece[][] board = new ChessPiece[8][8];

    public ChessPiece[][] getBoard() {
        return board.clone();
    }

    public void remove(Point point) {
        board[point.getX()][point.getY()] = null;
    }

    @Override
    public ChessPiece findByPoint(Point point) {
        return board[point.getX()][point.getY()];
    }

    @Override
    public ChessPiece findByPoint(int x, int y) {
        return board[x][y];
    }

    @Override
    public ChessPiece findByPoint(String strPoint) {
        Point point = new ChessPoint(strPoint);
        return findByPoint(point);
    }

    @Override
    public ChessPiece move(Piece piece, Point endPoint) {
        int startX = piece.getPoint().getX();
        int startY = piece.getPoint().getY();
        int endX = endPoint.getX();
        int endY = endPoint.getY();

        ChessPiece deadPiece = board[endX][endY];
        board[startX][startY] = null;
        board[endX][endY] = (ChessPiece) piece;
        piece.setPoint(endPoint);

        return deadPiece;
    }

    @Override
    public void init() {
        clear();
        setPawn();
        setRook();
        setKnight();
        setBishop();
        setQueen();
        setKing();
        log.info("Game Init");
    }

    private void setPawn() {
        Point[] twoLine = setPawnPointArray(2);
        Point[] sevenLine = setPawnPointArray(7);
        setPawn(twoLine, Name.TEAM_WHITE);
        setPawn(sevenLine, Name.TEAM_BLACK);
    }

    private Point[] setPawnPointArray(int row) {
        Point[] points = new Point[8];
        for (int i = 0; i < points.length; i++) {
            char a = (char) ('a' + i);
            String strPoint = String.valueOf(a) + row;
            Point point = new ChessPoint(strPoint);
            points[i] = point;
        }
        return points;
    }

    private void setPawn(Point[] points, String teamName) {
        for (Point point : points) {
            Pawn pawn = new Pawn(point, teamName);
            setInBoard(pawn, point);
        }
    }

    public void setInBoard(ChessPiece piece, Point point) {
        int x = point.getX();
        int y = point.getY();
        board[x][y] = piece;
    }

    private void setRook() {
        Point point1 = new ChessPoint("a1");
        Point point2 = new ChessPoint("h1");
        Point point3 = new ChessPoint("a8");
        Point point4 = new ChessPoint("h8");
        Rook rook1 = new Rook(point1, Name.TEAM_WHITE);
        Rook rook2 = new Rook(point2, Name.TEAM_WHITE);
        Rook rook3 = new Rook(point3, Name.TEAM_BLACK);
        Rook rook4 = new Rook(point4, Name.TEAM_BLACK);
        setInBoard(rook1, point1);
        setInBoard(rook2, point2);
        setInBoard(rook3, point3);
        setInBoard(rook4, point4);
    }

    private void setKnight() {
        Point point1 = new ChessPoint("b1");
        Point point2 = new ChessPoint("g1");
        Point point3 = new ChessPoint("b8");
        Point point4 = new ChessPoint("g8");
        Knight knight1 = new Knight(point1, Name.TEAM_WHITE);
        Knight knight2 = new Knight(point2, Name.TEAM_WHITE);
        Knight knight3 = new Knight(point3, Name.TEAM_BLACK);
        Knight knight4 = new Knight(point4, Name.TEAM_BLACK);
        setInBoard(knight1, point1);
        setInBoard(knight2, point2);
        setInBoard(knight3, point3);
        setInBoard(knight4, point4);
    }

    private void setBishop() {
        Point point1 = new ChessPoint("c1");
        Point point2 = new ChessPoint("f1");
        Point point3 = new ChessPoint("c8");
        Point point4 = new ChessPoint("f8");
        Bishop bishop1 = new Bishop(point1, Name.TEAM_WHITE);
        Bishop bishop2 = new Bishop(point2, Name.TEAM_WHITE);
        Bishop bishop3 = new Bishop(point3, Name.TEAM_BLACK);
        Bishop bishop4 = new Bishop(point4, Name.TEAM_BLACK);
        setInBoard(bishop1, point1);
        setInBoard(bishop2, point2);
        setInBoard(bishop3, point3);
        setInBoard(bishop4, point4);
    }

    private void setQueen() {
        Point point1 = new ChessPoint("d1");
        Point point2 = new ChessPoint("d8");
        Queen queen1 = new Queen(point1, Name.TEAM_WHITE);
        Queen queen2 = new Queen(point2, Name.TEAM_BLACK);
        setInBoard(queen1, point1);
        setInBoard(queen2, point2);
    }

    private void setKing() {
        Point point1 = new ChessPoint("e1");
        Point point2 = new ChessPoint("e8");
        King king1 = new King(point1, Name.TEAM_WHITE);
        King king2 = new King(point2, Name.TEAM_BLACK);
        setInBoard(king1, point1);
        setInBoard(king2, point2);
    }

    @Override
    public void clear() {
        for (ChessPiece[] chessPieces : board) {
            Arrays.fill(chessPieces, null);
        }
    }
}
