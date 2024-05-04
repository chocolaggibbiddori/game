package chocola.chess.piece;

import chocola.chess.Copyable;
import chocola.chess.Team;
import chocola.chess.Tile;

public abstract class Piece implements Copyable<Piece> {

    public final Team team;
    private Tile position;

    public Piece(Team team) {
        this.team = team;
    }

    protected Piece(Team team, Tile position) {
        this.team = team;
        this.position = position;
    }

    public Tile getPosition() {
        return position;
    }

    public void moveTo(Tile to) {
        position = to;
    }

    public abstract boolean canMoveTo(Tile to);

    public abstract String getInitial();

    public abstract Piece copy();
}
