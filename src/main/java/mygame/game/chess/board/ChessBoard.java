package mygame.game.chess.board;

import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.name.Name;
import mygame.game.chess.piece.*;
import mygame.piece.Piece;
import mygame.point.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChessBoard implements Board {

    private static final Piece[][] board = new Piece[8][8];

    public Piece[][] getBoard() {
        return board;
    }

    @Override
    public void init() {
        setPawn();
        setRook();
        setKnight();
        setBishop();
        setQueen();
        setKing();
        log.info("Game Init");
    }

    @Override
    public Piece findByPoint(Point point) {
        return board[point.getX()][point.getY()];
    }

    @Override
    public Piece findByPoint(int x, int y) {
        return board[x][y];
    }

    @Override
    public Piece findByPoint(String strPoint) {
        Point point = new Point(strPoint);
        return findByPoint(point);
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
            Point point = new Point(strPoint);
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

    private void setInBoard(Piece piece, Point point) {
        int x = point.getX();
        int y = point.getY();
        board[x][y] = piece;
    }

    private void setRook() {
        Point point1 = new Point("a1");
        Point point2 = new Point("h1");
        Point point3 = new Point("a8");
        Point point4 = new Point("h8");
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
        Point point1 = new Point("b1");
        Point point2 = new Point("g1");
        Point point3 = new Point("b8");
        Point point4 = new Point("g8");
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
        Point point1 = new Point("c1");
        Point point2 = new Point("f1");
        Point point3 = new Point("c8");
        Point point4 = new Point("f8");
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
        Point point1 = new Point("d1");
        Point point2 = new Point("d8");
        Queen queen1 = new Queen(point1, Name.TEAM_WHITE);
        Queen queen2 = new Queen(point2, Name.TEAM_BLACK);
        setInBoard(queen1, point1);
        setInBoard(queen2, point2);
    }

    private void setKing() {
        Point point1 = new Point("e1");
        Point point2 = new Point("e8");
        King king1 = new King(point1, Name.TEAM_WHITE);
        King king2 = new King(point2, Name.TEAM_BLACK);
        setInBoard(king1, point1);
        setInBoard(king2, point2);
    }
}
