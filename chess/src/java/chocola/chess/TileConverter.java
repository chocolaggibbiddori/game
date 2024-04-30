package chocola.chess;

public class TileConverter {

    private TileConverter() {
    }

    public static Tile convertToTile(int file, int rank) {
        check(file, rank);

        String tileStr = "";
        tileStr += (char) ('A' + file);
        tileStr += rank + 1;

        return Tile.valueOf(tileStr);
    }

    private static void check(int file, int rank) {
        if (file < 0 || file >= ChessBoard.FILE_NUM || rank < 0 || rank >= ChessBoard.RANK_NUM) throw new IllegalArgumentException();
    }
}
