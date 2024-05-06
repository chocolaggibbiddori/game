package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public abstract class MoveCheckedPiece extends Piece {

    private boolean moved;

    protected MoveCheckedPiece(Team team, Tile position, boolean moved) {
        super(team, position);
        this.moved = moved;
    }

    public final boolean isMoved() {
        return moved;
    }

    @Override
    public void moveTo(Tile to) {
        super.moveTo(to);
        moved = true;
    }

    public abstract MoveCheckedPiece copy();
}
