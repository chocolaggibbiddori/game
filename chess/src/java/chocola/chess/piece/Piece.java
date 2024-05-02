package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public abstract class Piece {

    public final Team team;
    private Tile position;

    public Piece(Team team) {
        this.team = team;
    }

    public Tile getPosition() {
        return position;
    }

    public void moveTo(Tile to) {
        position = to;
    }

    public abstract boolean canMoveTo(Tile to);

    public abstract char getInitial();
}
