package chocola.chess.piece;

import chocola.chess.Copyable;
import chocola.chess.Team;
import chocola.chess.Tile;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece implements Copyable<Piece> {

    public final Team team;
    private Tile position;

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

    public Set<Tile> getMoveableTileSet() {
        return Arrays.stream(Tile.values())
                .filter(this::canMoveTo)
                .collect(Collectors.toSet());
    }

    public abstract boolean canMoveTo(Tile to);

    public abstract String getInitial();

    public abstract Piece copy();
}
